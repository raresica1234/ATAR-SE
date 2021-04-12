package client.model

import javafx.beans.property.SimpleBooleanProperty

data class SelectedUserItemModel(
    var id: Int,
    var fullName: String,
    var email: String,
    var selected: SimpleBooleanProperty = SimpleBooleanProperty()
) {
    override fun toString() = "$fullName - $email"
}