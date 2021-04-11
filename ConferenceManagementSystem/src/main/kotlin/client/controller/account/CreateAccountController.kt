package client.controller.account

import client.model.account.CreateAccountModel
import javafx.scene.control.ButtonType
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.eq
import utils.isNullOrBlank

class CreateAccountController : Controller() {
    val model = CreateAccountModel()

    fun handleCreateAccountClick(): Boolean {
        try {
            validateFields()
            UserService.createAccount(model.toUser())
        } catch (exception: ValidationException) {
            tornadofx.error(exception.title, exception.message, ButtonType.OK)
            return false
        }
        return true
    }

    private fun validateFields() {
        if (model.email.isNullOrBlank() ||
            model.password.isNullOrBlank() ||
            model.confirmPassword.isNullOrBlank() ||
            model.firstName.isNullOrBlank() ||
            model.lastName.isNullOrBlank() ||
            model.affiliation.isNullOrBlank()
        ) {
            throw ValidationException(
                "Not all fields completed!",
                "There are some mandatory fields that have not been filled in. Please check them and try again."
            )
        }
        if (!model.password.eq(model.confirmPassword)) {
            throw ValidationException(
                "Passwords do not match!",
                "The given password does not match the confirm password, please try again."
            )
        }
    }
}