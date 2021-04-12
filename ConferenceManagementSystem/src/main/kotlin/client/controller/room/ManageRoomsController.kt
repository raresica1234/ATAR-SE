package client.controller.room

import client.model.RoomItemModel
import client.model.room.ManageRoomsModel
import server.service.RoomSerivce
import tornadofx.Controller

class ManageRoomsController : Controller() {
    val model = ManageRoomsModel()

    fun refreshRooms() {
        model.rooms.setAll(RoomSerivce.getAll().map {
            RoomItemModel(it.id, it.seatCount)
        })
    }
}