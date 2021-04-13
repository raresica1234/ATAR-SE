package client.controller.conference

import client.model.SelectedUserItemModel
import client.model.UserItemModel
import client.model.conference.ModifyConferenceModel
import client.state.userState
import javafx.beans.property.SimpleBooleanProperty
import server.domain.Conference
import server.domain.RoleType
import server.domain.User
import server.service.ConferenceService
import server.service.RoleService
import server.service.UserService
import server.service.UserWithRole
import tornadofx.Controller
import tornadofx.onChange
import utils.ValidationException
import utils.isValid

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
        runAsync {
            ConferenceService.get(conferenceId)?.let {
                initialConference = it
            }

            val users = UserService.getUsersWithRole(userState.user.id, conferenceId)
                .filter { it.roleType != RoleType.AUTHOR || it.roleType != RoleType.LISTENER }

            val usersWithSelection = users.map {
                SelectedUserItemModel(
                    it.user.id,
                    it.user.fullName,
                    it.user.email,
                    SimpleBooleanProperty(it.roleType == RoleType.PROGRAM_COMMITTEE).apply {
                        onChange { checked -> handleCommitteeSelection(it.user, checked) }
                    }
                )
            }
            users to usersWithSelection
        } ui {
            model.setConference(initialConference)
            setChairsSource(it.first)
            model.sources.committees.setAll(it.second)
            selectChair(it.first)
            model.searchedCommittees.setAll(model.sources.committees)
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

        selectedChair.set(sources.chairs.find { it.id == coChairId })

        model.selectedChair.addListener { _, previousChair, selectedChair ->
            handleSelectedChair(previousChair, selectedChair, users)

            applyCommitteesSearch(selectedChair = selectedChair)
        }
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

        // obtain the user that is now selected and was checked as program committee
        val exCommittee = model.sources.committees.find { it.id == selectedChair.id && it.selected.value }

        // if the user did not have a previous role, add it
        if (exCommittee == null) {
            RoleService.add(selectedChair.id, conferenceId, RoleType.CHAIR)
            return
        }

        // otherwise, update it
        RoleService.update(exCommittee.id, conferenceId, RoleType.CHAIR)

        // propagate changes to the view
        exCommittee.selected.set(false)
        users.find { it.user.id == selectedChair.id }?.roleType = null
        users.find { it.user.id == exCommittee.id }?.roleType = RoleType.CHAIR
    }

    private fun handleCommitteeSelection(user: User, checked: Boolean) {
        val conferenceId = model.id.get()

        if (checked) {
            RoleService.add(user.id, conferenceId, RoleType.PROGRAM_COMMITTEE)
        } else {
            RoleService.delete(user.id, conferenceId)
        }
    }
}