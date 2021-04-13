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
        with(model) {
            id.onChange {
                if (it != 0) {
                    fetchData(it)
                }
            }
            search.onChange { applyCommitteesSearch(it) }
        }
    }

    private fun fetchData(conferenceId: Int) {
        ConferenceService.get(conferenceId)?.let {
            initialConference = it
            model.setConference(it)
        }

        val users = UserService.getUsersWithRole(userState.user.id, conferenceId)
            .filter { it.roleType != RoleType.AUTHOR || it.roleType != RoleType.LISTENER }

        setChairsSource(users)
        setCommitteesSource(users)
        selectChair(users)
        model.searchedCommittees.setAll(model.sources.committees)
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

    private fun setCommitteesSource(users: List<UserWithRole>) {
        model.selectedChair.addListener { _, previousChair, selectedChair ->
            handleSelectedChair(previousChair, selectedChair, users)

            model.sources.committees.setAll(
                users.filter { it.user.id != selectedChair?.id ?: 0 }
                    .map {
                        SelectedUserItemModel(
                            it.user.id,
                            it.user.fullName,
                            it.user.email,
                            SimpleBooleanProperty(it.roleType == RoleType.PROGRAM_COMMITTEE).apply {
                                onChange { checked -> handleCommitteeSelection(it.user, checked) }
                            }
                        )
                    })

            applyCommitteesSearch()
        }
    }

    private fun selectChair(users: List<UserWithRole>) = with(model) {
        val coChairId = users.find { it.roleType == RoleType.CHAIR }?.user?.id ?: 0

        selectedChair.set(sources.chairs.find { it.id == coChairId })
    }

    private fun applyCommitteesSearch(search: String? = model.search.get()) {
        val searchValue = search?.trim()

        if (searchValue == null) {
            model.searchedCommittees.setAll(model.sources.committees)
            return
        }

        model.searchedCommittees.setAll(model.sources.committees.filtered {
            it.email.contains(searchValue, true) || it.fullName.contains(searchValue, true)
        })
    }

    private fun handleSelectedChair(previousChair: UserItemModel?, selectedChair: UserItemModel?, users: List<UserWithRole>) {
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