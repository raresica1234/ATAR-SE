package client.controller

import client.model.LoginModel
import javafx.scene.control.ButtonType
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.getOrEmpty
import utils.isNullOrBlank

class LoginController(): Controller() {
    val loginModel = LoginModel()

    fun handleLoginClick() : Boolean {
        return try {
            validateFields()
            loginModel.user = UserService.login(loginModel.email.get(), loginModel.password.getOrEmpty())
            true
        } catch (exception: ValidationException) {
            tornadofx.error(exception.title, exception.message, ButtonType.OK)
            false
        }
    }

    private fun validateFields() {
        if (loginModel.email.isNullOrBlank()) {
            throw ValidationException(
                "No email provided!",
                "The email is mandatory, fill it and try again."
            )
        }
    }
}