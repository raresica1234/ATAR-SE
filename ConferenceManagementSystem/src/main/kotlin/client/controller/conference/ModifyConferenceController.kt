package client.controller.conference

import client.model.conference.ModifyConferenceModel
import server.domain.Conference
import server.service.ConferenceService
import tornadofx.Controller
import tornadofx.onChange
import utils.ValidationException
import utils.isValid

class ModifyConferenceController: Controller() {
    val model: ModifyConferenceModel = ModifyConferenceModel()

    private var initialConference: Conference = Conference {}

    init {
        model.id.onChange {
            if (it != 0) {
                fetchData(it)
            }
        }
    }

    private fun fetchData(conferenceId: Int) {
        ConferenceService.get(conferenceId)?.let {
            initialConference = it
            model.setConference(it)
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
}