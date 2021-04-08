package client.model

import javafx.beans.property.SimpleStringProperty
import server.domain.User

data class LoginModel(
    var user: User = User{},
    var email: SimpleStringProperty = SimpleStringProperty(),
    var password: SimpleStringProperty = SimpleStringProperty()
)
