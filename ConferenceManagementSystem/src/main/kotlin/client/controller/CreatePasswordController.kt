package client.controller

import javafx.scene.control.ButtonType
import server.domain.User
import server.service.UserService.Companion.setAccountPassword
import tornadofx.Controller
import utils.ValidationException

class CreatePasswordController(var user: User = User{}, var password: String = "", var confirmPasword: String = "") : Controller() {
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
        if (password.isEmpty()) {
            throw ValidationException(
                "Password not set!",
                "The password must be set before proceeding, try again."
            )
        }
        if (confirmPasword.isEmpty()) {
            throw ValidationException(
                "Password confirmation not set!",
                "The password confirmation must be set before proceeding, try again."
            )
        }
        if (password != confirmPasword) {
            throw ValidationException(
                "Passwords do not match!",
                "The passwords must match, try again."
            )
        }
    }

    private fun setPassword() {
        setAccountPassword(user, password)
    }
}