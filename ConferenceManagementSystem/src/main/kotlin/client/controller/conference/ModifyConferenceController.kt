package client.controller.conference

import client.model.conference.ModifyConferenceModel
import server.service.ConferenceService
import tornadofx.Controller
import tornadofx.onChange

class ModifyConferenceController: Controller() {
    val model: ModifyConferenceModel = ModifyConferenceModel()

    init {
        model.id.onChange {
            if (it != 0) {
                fetchData(it)
            }
        }
    }

    private fun fetchData(conferenceId: Int) {
        println(ConferenceService.getConference(conferenceId))
    }
}