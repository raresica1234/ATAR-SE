package client.controller.account

import client.model.account.CreatePasswordModel
import client.state.userState
import javafx.scene.control.ButtonType
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.isNullOrBlank

class CreatePasswordController : Controller() {
    val model = CreatePasswordModel()

    fun handleCreatePassword(): Boolean {
        return try {
            validateFields()
            userState.user = UserService.update(model.toUser())
            true
        } catch (exception: ValidationException) {
            exception.displayError()
            false
        }
    }

    private fun validateFields() {
        if (model.password.isNullOrBlank()) {
            throw ValidationException(
                "Password not set!",
                "The password must be set before proceeding, try again."
            )
        }
        if (model.confirmPassword.isNullOrBlank()) {
            throw ValidationException(
                "Password confirmation not set!",
                "The password confirmation must be set before proceeding, try again."
            )
        }
        if (model.password.get() != model.confirmPassword.get()) {
            throw ValidationException(
                "Passwords do not match!",
                "The passwords must match, try again."
            )
        }
    }
}