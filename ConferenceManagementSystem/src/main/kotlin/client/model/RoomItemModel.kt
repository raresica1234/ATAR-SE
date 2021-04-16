package client.model

import javafx.beans.property.SimpleIntegerProperty
import server.domain.Room

data class RoomItemModel(
    val id: Int = 0,
    var seats: Int = 0
) {
    override fun toString() = "Room with $seats seats"

    fun toRoom() = Room{
        id = this@RoomItemModel.id
        seatCount = this@RoomItemModel.seats
    }
}