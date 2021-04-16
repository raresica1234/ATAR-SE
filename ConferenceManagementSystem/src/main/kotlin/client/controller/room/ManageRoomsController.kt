package client.controller.room

import client.model.RoomItemModel
import client.model.room.ManageRoomsModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import server.service.RoomSerivce
import tornadofx.Controller
import utils.setObject

class ManageRoomsController : Controller() {
    val rooms: ObservableList<RoomItemModel> = FXCollections.observableArrayList()

    fun refreshRooms() {
        rooms.setAll(RoomSerivce.getAll().map {
            RoomItemModel(it.id, it.seatCount)
        })
    }

    fun handleDeleteRoom(roomId: Int) {
        rooms.removeIf { it.id == roomId }
        RoomSerivce.delete(roomId)
    }

    fun handleAdd(seatCount: Int) = RoomSerivce.add(seatCount).let {
        rooms.add(RoomItemModel(it.id, it.seatCount))
    }

    fun handleEdit(oldRoom: RoomItemModel, newRoom: RoomItemModel) {
        rooms.setObject(oldRoom, newRoom)
        RoomSerivce.update(newRoom.toRoom())
    }
}