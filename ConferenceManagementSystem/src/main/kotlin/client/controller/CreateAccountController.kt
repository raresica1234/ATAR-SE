package client.controller

import client.model.CreateAccountModel
import javafx.scene.control.ButtonType
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.eq
import utils.isNullOrBlank

class CreateAccountController : Controller() {
    val createAccountModel = CreateAccountModel()

    fun handleOnDock() {
        createAccountModel.clear()
    }

    fun handleCreateAccountClick(): Boolean {
        try {
            validateFields()
            UserService.createAccount(createAccountModel.toUser())
        } catch (exception: ValidationException) {
            tornadofx.error(exception.title, exception.message, ButtonType.OK)
            return false
        }
        return true
    }

    private fun validateFields() {
        if (createAccountModel.email.isNullOrBlank() ||
            createAccountModel.password.isNullOrBlank() ||
            createAccountModel.confirmPassword.isNullOrBlank() ||
            createAccountModel.firstName.isNullOrBlank() ||
            createAccountModel.lastName.isNullOrBlank() ||
            createAccountModel.affiliation.isNullOrBlank()
        ) {
            throw ValidationException(
                "Not all fields completed!",
                "There are some mandatory fields that have not been filled in. Please check them and try again."
            )
        }
        if (!createAccountModel.password.eq(createAccountModel.confirmPassword)) {
            throw ValidationException(
                "Passwords do not match!",
                "The given password does not match the confirm password, please try again."
            )
        }
    }
}