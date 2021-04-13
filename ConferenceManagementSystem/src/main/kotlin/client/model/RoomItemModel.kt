package client.model

import javafx.beans.property.SimpleIntegerProperty

data class RoomItemModel(
    val id: Int = 0,
    var seats: Int = 0
) {
    override fun toString() = "Room with $seats seats"
}