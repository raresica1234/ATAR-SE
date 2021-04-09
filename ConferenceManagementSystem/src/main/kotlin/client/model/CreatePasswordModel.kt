package client.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import server.domain.User

data class CreatePasswordModel(
    var user: SimpleObjectProperty<User> = SimpleObjectProperty(),
    var password: SimpleStringProperty = SimpleStringProperty(),
    var confirmPasword: SimpleStringProperty = SimpleStringProperty()
) {
    fun toUser(): User {
        val user = user.get()

        user.password = password.get()

        return user
    }
}
