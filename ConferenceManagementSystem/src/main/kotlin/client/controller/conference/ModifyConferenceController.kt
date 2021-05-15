package client.controller.conference

import client.model.ProposalItemModel
import client.model.RoomItemModel
import client.model.SelectedUserItemModel
import client.model.UserItemModel
import client.model.conference.ModifyConferenceModel
import client.model.conference.ModifyConferenceSectionModel
import client.state.userState
import javafx.beans.property.SimpleBooleanProperty
import server.domain.Conference
import server.domain.RoleType
import server.domain.Section
import server.domain.User
import server.service.*
import tornadofx.Controller
import tornadofx.onChange
import utils.ValidationException
import utils.isValid
import utils.validateBefore
import java.time.LocalDate

class ModifyConferenceController : Controller() {
    val model: ModifyConferenceModel = ModifyConferenceModel()

    private var initialConference: Conference = Conference {}

    init {
        model.search.onChange { applyCommitteesSearch(it) }
    }

    fun refreshData() {
        val conferenceId = model.id.get()

        if (conferenceId != 0) {
            fetchData(conferenceId)
        }
    }

    private fun fetchData(conferenceId: Int) {
        data class FetchedData(
            val allUsers: List<UserItemModel>,
            val usersWithRoles: List<UserWithRole>,
            val usersWithSelection: List<SelectedUserItemModel>,
            val sections: List<ModifyConferenceSectionModel>,
            val rooms: List<RoomItemModel>
        )

        runAsync {
            ConferenceService.get(conferenceId)?.let {
                initialConference = it
            }

            val allUsers = UserService.getAll().map { UserItemModel(it.id, it.fullName, it.email) }

            val usersWithRoles = UserService.getUsersWithRole(userState.user.id, conferenceId)
                .filter { it.roleType != RoleType.AUTHOR || it.roleType != RoleType.LISTENER }

            val usersWithSelection = usersWithRoles.map {
                SelectedUserItemModel(
                    it.user.id,
                    it.user.fullName,
                    it.user.email,
                    SimpleBooleanProperty(it.roleType == RoleType.PROGRAM_COMMITTEE).apply {
                        onChange { checked -> handleCommitteeSelection(it.user, checked) }
                    }
                )
            }

            val sections = SectionService.getAllWithProposalsByConference(initialConference.id).map {
                ModifyConferenceSectionModel.from(it) { proposalId ->
                    ProposalService.getProposalAuthors(proposalId).joinToString { user -> user.fullName }
                }
            }

            val rooms = RoomService.getAll().map { RoomItemModel(it.id, it.seatCount) }

            FetchedData(allUsers, usersWithRoles, usersWithSelection, sections, rooms)
        } ui {
            model.setConference(initialConference)
            model.sources.committees.setAll(it.usersWithSelection)
            model.sources.users.setAll(it.allUsers)
            model.sources.rooms.setAll(it.rooms)
            setChairsSource(it.usersWithRoles)
            selectChair(it.usersWithRoles)
            model.sections.setAll(it.sections)
            model.selectedSection.set(ModifyConferenceSectionModel())
            model.isLoading.set(false)
        }
    }

    fun updateConference() {
        if (!areConferenceFieldsValid()) {
            return
        }

        val updatedConference = model.toConference()
        if (initialConference == updatedConference) {
            return
        }

        try {
            ConferenceService.update(updatedConference);
        } catch (exception: ValidationException) {
            exception.displayError()
        }
    }

    fun saveSection() {
        val selectedSection = model.selectedSection.get()

        if (selectedSection.id.get() != 0) {
            return updateSection(selectedSection)
        }

        val section = selectedSection.toSection(initialConference.id)

        try {
            validateSection(section)
        } catch (exception: ValidationException) {
            exception.displayError()
            return
        }

        SectionService.add(section)

        model.sections.add(model.selectedSection.get())
        model.selectedSection.set(ModifyConferenceSectionModel())
    }

    fun deleteSection(section: ModifyConferenceSectionModel) {
        SectionService.delete(section.id.get())

        model.sections.remove(section)
    }

    fun getProposals() = ProposalService.getAllUnassociatedForConference(initialConference.id)

    fun addProposal(proposalId: Int) {
        val section = model.selectedSection.get()

        ProposalService.setToSection(proposalId, section.id.get())
        ProposalService.getWithAuthors(proposalId)?.apply {
            section.proposals.add(ProposalItemModel(proposalId, proposal.name, authors.joinToString { it.fullName }))
        }
    }

    fun removeProposal(proposalId: Int) {
        ProposalService.setToSection(proposalId)
        model.selectedSection.get().proposals.removeIf { it.id == proposalId }
    }

    private fun updateSection(section: ModifyConferenceSectionModel) {
        val sectionToUpdate = section.toSection(initialConference.id)

        try {
            validateSection(sectionToUpdate)
        } catch (exception: ValidationException) {
            exception.displayError()
            return
        }

        SectionService.update(sectionToUpdate)
    }

    private fun areConferenceFieldsValid() = with(model) {
        name.isValid() &&
                abstractDeadline.isValid() &&
                paperDeadline.isValid() &&
                biddingDeadline.isValid() &&
                reviewDeadline.isValid()
    }

    private fun setChairsSource(users: List<UserWithRole>) = with(model.sources) {
        chairs.clear()
        chairs.add(UserItemModel())
        chairs.addAll(users.map { UserItemModel(it.user.id, it.user.fullName, it.user.email) })
    }

    private fun selectChair(users: List<UserWithRole>) = with(model) {
        val coChairId = users.find { it.roleType == RoleType.CHAIR }?.user?.id ?: 0

        selectedChair.addListener { _, previousChair, selectedChair ->
            if (!isLoading.get()) {
                handleSelectedChair(previousChair, selectedChair, users)
            }

            applyCommitteesSearch(selectedChair = selectedChair)
        }

        selectedChair.set(sources.chairs.find { it.id == coChairId })
    }

    private fun applyCommitteesSearch(
        search: String? = model.search.get(),
        selectedChair: UserItemModel? = model.selectedChair.get()
    ) {
        val searchValue = search?.trim()
        val committeesWithoutChair = model.sources.committees.filtered { it.id != selectedChair?.id ?: 0 }

        if (searchValue == null) {
            model.searchedCommittees.setAll(committeesWithoutChair)
            return
        }

        model.searchedCommittees.setAll(committeesWithoutChair.filtered {
            it.email.contains(searchValue, true) || it.fullName.contains(searchValue, true)
        })
    }

    private fun handleSelectedChair(
        previousChair: UserItemModel?,
        selectedChair: UserItemModel?,
        users: List<UserWithRole>
    ) {
        val conferenceId = model.id.get()

        // if selectedChair is set to null, data is being deallocated
        if (selectedChair == null) {
            return
        }

        // remove only if there was a previous co-chair
        previousChair?.let { RoleService.delete(previousChair.id, conferenceId) }

        // if selection set to none, stop here
        if (selectedChair.id == 0) {
            return
        }

        // unselect PC member that becomes chair
        model.sources.committees.find { it.id == selectedChair.id }?.selected?.set(false)

        // Add or update role
        RoleService.addOrUpdate(selectedChair.id, conferenceId, RoleType.CHAIR)

        // propagate changes to the view
        users.find { it.user.id == selectedChair.id }?.roleType = null
        users.find { it.user.id == selectedChair.id }?.roleType = RoleType.CHAIR
    }

    private fun handleCommitteeSelection(user: User, checked: Boolean) {
        val conferenceId = model.id.get()

        if (checked) {
            RoleService.add(user.id, conferenceId, RoleType.PROGRAM_COMMITTEE)
        } else {
            RoleService.delete(user.id, conferenceId, RoleType.PROGRAM_COMMITTEE)
        }
    }

    private fun validateSection(section: Section) = with(section) {
        if (conferenceId == 0 ||
            name.isBlank() ||
            sessionChairId == 0 ||
            startDate == LocalDate.EPOCH ||
            endDate == LocalDate.EPOCH ||
            roomId == 0
        ) {
            throw ValidationException(
                "Some fields are empty",
                "All fields must be filled in, please check them and try again!"
            )
        }

        startDate.validateBefore(endDate, true)

        if (!SectionService.isRoomAvailable(roomId, startDate, endDate, section.id)) {
            throw ValidationException(
                "Room is in use",
                "Please change the room or adjust the dates such that another section's room is not overlapping with" +
                        "the current one!"
            )
        }
    }
}