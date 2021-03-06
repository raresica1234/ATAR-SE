package client.controller.account

import client.model.account.LoginModel
import client.state.userState
import javafx.scene.control.ButtonType
import server.domain.User
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.getOrEmpty
import utils.isNullOrBlank

class LoginController : Controller() {
    val model = LoginModel()

    fun handleLoginClick(): User? {
        return try {
            validateFields()
            userState.user = UserService.login(model.email.get(), model.password.getOrEmpty())
            userState.user
        } catch (exception: ValidationException) {
            exception.displayError()
            null
        }
    }

    private fun validateFields() {
        if (model.email.isNullOrBlank()) {
            throw ValidationException(
                "No email provided!",
                "The email is mandatory, fill it and try again."
            )
        }
    }
}