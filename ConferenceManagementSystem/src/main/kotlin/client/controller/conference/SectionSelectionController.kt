package client.controller.conference

import client.model.conference.SectionModel
import client.model.conference.SectionSelectionModel
import client.state.userState
import server.domain.RoleType
import server.service.ParticipantService
import server.service.RoleService
import server.service.SectionService
import tornadofx.Controller
import utils.clear

class SectionSelectionController : Controller() {
    val model = SectionSelectionModel()

    fun onParamsSet(conferenceId: Int) {
        model.selectedSection.clear()
        model.conferenceId.set(conferenceId)

        refreshData(conferenceId)
    }

    private fun refreshData(conferenceId: Int) {
        runAsync {
            SectionService.getAllByConferenceId(conferenceId).map {
                SectionModel(it.id, it.name)
            }
        } ui {
            model.sections.setAll(it)
        }
    }

    fun onContinue() {
        val userId = userState.user.id

        model.selectedSection.get()?.let {
            ParticipantService.addParticipant(it.id, userId)
        }

        RoleService.add(userId, model.conferenceId.get(), RoleType.LISTENER)
    }
}