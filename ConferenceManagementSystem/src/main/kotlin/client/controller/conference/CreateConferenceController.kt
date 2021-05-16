package client.controller.conference

import client.model.UserItemModel
import client.model.conference.CreateConferenceModel
import client.state.userState
import server.service.ConferenceService
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.isNull
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

        if (model.reviewerCount.isNull()) throw ValidationException(
            "Not all fields completed!",
            "'Maximum reviewers' is a mandatory field that has not been filled in. Please check it and try again."
        )

        if (model.reviewerCount.get() !in 2..10) throw ValidationException(
            "Number of of range!",
            "'Maximum reviewers' range is between 2 and 10. The given value is outside of the required range. " +
                    "Please check it and try again."
        )
    }

}
