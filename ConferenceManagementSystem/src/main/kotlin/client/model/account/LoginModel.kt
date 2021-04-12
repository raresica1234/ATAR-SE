package client.model.account

import javafx.beans.property.SimpleStringProperty

data class LoginModel(
    var email: SimpleStringProperty = SimpleStringProperty("admin@mail.com"),
    var password: SimpleStringProperty = SimpleStringProperty("admin")
)