package client.controller.room

import client.model.RoomItemModel
import client.model.room.ManageRoomsModel
import server.service.RoomSerivce
import tornadofx.Controller
import utils.setObject

class ManageRoomsController : Controller() {
    val model = ManageRoomsModel()

    fun refreshRooms() {
        model.rooms.setAll(RoomSerivce.getAll().map {
            RoomItemModel(it.id, it.seatCount)
        })
    }

    fun handleDeleteRoom(roomId: Int) {
        model.rooms.removeIf { it.id == roomId }
        RoomSerivce.delete(roomId)
    }

    fun handleAdd(seatCount: Int) = RoomSerivce.add(seatCount).let {
        model.rooms.add(RoomItemModel(it.id, it.seatCount))
    }

    fun handleEdit(oldRoom: RoomItemModel, newRoom: RoomItemModel) {
        model.rooms.setObject(oldRoom, newRoom)
        RoomSerivce.update(newRoom.toRoom())
    }
}