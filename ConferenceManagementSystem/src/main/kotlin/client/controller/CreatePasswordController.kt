package client.controller

import client.model.CreatePasswordModel
import javafx.scene.control.ButtonType
import server.service.UserService.Companion.setAccountPassword
import tornadofx.Controller
import utils.ValidationException

class CreatePasswordController() : Controller() {
    val model = CreatePasswordModel()

    fun handleCreatePassword(): Boolean {
        return try {
            validateFields()
            setPassword()
            true;
        } catch (exception: ValidationException) {
            tornadofx.error(exception.title, exception.message, ButtonType.OK)
            false;
        }
    }

    private fun validateFields() {
        if (model.password.get().isEmpty()) {
            throw ValidationException(
                "Password not set!",
                "The password must be set before proceeding, try again."
            )
        }
        if (model.confirmPasword.get().isEmpty()) {
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

    private fun setPassword() {
        setAccountPassword(model.user, model.password.get())
    }
}