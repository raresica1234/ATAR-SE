package client.controller.conference

import client.model.UserItemModel
import client.model.conference.CreateConferenceModel
import client.state.userState
import javafx.scene.control.ButtonType
import server.service.ConferenceService
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.isNullOrBlank
import utils.isObjectNull

class CreateConferenceController : Controller() {
    val model = CreateConferenceModel()

    fun refreshChairs() {
        val chairs = UserService.getAll(userState.user.id)
            .map { UserItemModel(it.id, it.fullName, it.email) }

        model.chairs.setAll(chairs)
    }

    fun handleCreateConferenceClick(): Boolean {
        try {
            validateFields()
            ConferenceService.createConference(model.toConference(), model.getChairId())
        } catch (exception: ValidationException) {
            exception.displayError()
            return false
        }
        return true
    }

    private fun validateFields() {
        if (model.name.isNullOrBlank() || model.chair.isObjectNull()) {
            throw ValidationException(
                "Not all fields completed!",
                "'Name' and 'Chair' are mandatory fields that have not been filled in. Please check them and try again."
            )
        }
    }

}
