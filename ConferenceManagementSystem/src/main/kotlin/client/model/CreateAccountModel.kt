package client.model

import javafx.beans.property.SimpleStringProperty
import server.domain.User
import utils.clear

data class CreateAccountModel(
    val email: SimpleStringProperty = SimpleStringProperty(),
    val password: SimpleStringProperty = SimpleStringProperty(),
    val confirmPassword: SimpleStringProperty = SimpleStringProperty(),
    val firstName: SimpleStringProperty = SimpleStringProperty(),
    val lastName: SimpleStringProperty = SimpleStringProperty(),
    val affiliation: SimpleStringProperty = SimpleStringProperty(),
    val webpageLink: SimpleStringProperty = SimpleStringProperty()
) {
    fun toUser() = User {
        email = this@CreateAccountModel.email.get()
        password = this@CreateAccountModel.password.get()
        firstName = this@CreateAccountModel.firstName.get()
        lastName = this@CreateAccountModel.lastName.get()
        affiliation = this@CreateAccountModel.affiliation.get()
        webpageLink = this@CreateAccountModel.webpageLink.get().orEmpty()
        isSiteAdministrator = false
    }

    fun clear() {
        email.clear()
        password.clear()
        confirmPassword.clear()
        firstName.clear()
        lastName.clear()
        affiliation.clear()
        webpageLink.clear()
    }
}