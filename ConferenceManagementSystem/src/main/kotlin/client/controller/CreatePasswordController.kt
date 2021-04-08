package client.controller

import client.model.CreatePasswordModel
import javafx.scene.control.ButtonType
import server.service.UserService.Companion.setAccountPassword
import tornadofx.Controller
import utils.ValidationException
import utils.getOrEmpty
import utils.isNullOrBlank

class CreatePasswordController() : Controller() {
    val model = CreatePasswordModel()

    fun handleCreatePassword(): Boolean {
        return try {
            validateFields()
            setAccountPassword(model.user.get(), model.password.get())
            true;
        } catch (exception: ValidationException) {
            tornadofx.error(exception.title, exception.message, ButtonType.OK)
            false;
        }
    }

    private fun validateFields() {
        if (model.password.isNullOrBlank()) {
            throw ValidationException(
                "Password not set!",
                "The password must be set before proceeding, try again."
            )
        }
        if (model.confirmPasword.isNullOrBlank()) {
            throw ValidationException(
                "Password confirmation not set!",
                "The password confirmation must be set before proceeding, try again."
            )
        }
        if (model.password.get() != model.confirmPasword.get()) {
            throw ValidationException(
                "Passwords do not match!",
                "The passwords must match, try again."
            )
        }
    }
}