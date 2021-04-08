package client.controller

import client.model.LoginModel
import javafx.scene.control.ButtonType
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException

class LoginController(): Controller() {
    val loginModel = LoginModel()

    fun handleLoginClick() : Boolean {
        return try {
            validateFields()
            loginModel.user = UserService.login(loginModel.email.get(), loginModel.password.get())
            true
        } catch (exception: ValidationException) {
            tornadofx.error(exception.title, exception.message, ButtonType.OK)
            false
        }
    }

    private fun validateFields() {
        if (loginModel.email.get().isEmpty()) {
            throw ValidationException(
                "No email provided!",
                "The email is mandatory, fill it and try again."
            )
        }
    }
}