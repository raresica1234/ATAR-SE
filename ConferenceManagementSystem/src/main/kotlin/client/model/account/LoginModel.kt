package client.model.account

import javafx.beans.property.SimpleStringProperty

data class LoginModel(
    var email: SimpleStringProperty = SimpleStringProperty("google.chair@mail.com"),
    var password: SimpleStringProperty = SimpleStringProperty("google.chair")
)