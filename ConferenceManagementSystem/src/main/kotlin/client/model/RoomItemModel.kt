package client.model

import javafx.beans.property.SimpleIntegerProperty

data class RoomItemModel(
    val id: SimpleIntegerProperty = SimpleIntegerProperty(),
    val seats: SimpleIntegerProperty = SimpleIntegerProperty()
) {
    override fun toString() = "Room with $seats seats"
}