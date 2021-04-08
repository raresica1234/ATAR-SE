package client.model

import javafx.beans.property.SimpleStringProperty
import server.domain.User

data class CreatePasswordModel(
    var user: User = User{},
    var password: SimpleStringProperty = SimpleStringProperty(),
    var confirmPasword: SimpleStringProperty = SimpleStringProperty()
)
