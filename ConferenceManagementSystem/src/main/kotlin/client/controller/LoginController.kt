package client.controller

import client.model.LoginState
import javafx.scene.control.ButtonType
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import server.database
import server.service.UserService.Companion.getAccountFromEmail
import server.users
import tornadofx.Controller
import utils.ValidationException

class LoginController(var emailText: String = "", var passwordText: String = ""): Controller() {
    fun handleLoginClick() : LoginState {
        println("Email: $emailText\nPassword: $passwordText")

        try {
            validateFields()
            return login()
        } catch (exception: ValidationException) {
            tornadofx.error(exception.title, exception.message, ButtonType.OK)
            return LoginState.LOGIN_FAILED
        }
    }

    private fun validateFields() {
        if (emailText.isEmpty()) {
            throw ValidationException(
                "No email provided!",
                "The email is mandatory, fill it and try again."
            )
        }
    }

    fun login() : LoginState {
        val user = getAccountFromEmail(emailText)
            ?: throw ValidationException(
                "User does not exist!",
                "The email provided is not associated with any user, try creating an account first."
            )

        if (user.password == "") {
            return LoginState.LOGIN_GUEST_USER
        }
        else if (user.password != passwordText) {
            throw ValidationException(
                "Password incorrect!",
                "The given password does not match, please try again."
            )
        }
        return LoginState.LOGIN_USER
    }
}