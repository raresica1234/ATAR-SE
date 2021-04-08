package client.model

import javafx.beans.property.SimpleStringProperty

data class LoginModel(
    var email: SimpleStringProperty = SimpleStringProperty(),
    var password: SimpleStringProperty = SimpleStringProperty()
)