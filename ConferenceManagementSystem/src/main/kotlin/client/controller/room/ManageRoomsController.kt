package client.controller.room

import client.model.RoomItemModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import server.service.RoomService
import tornadofx.Controller
import utils.setObject

class ManageRoomsController : Controller() {
    val rooms: ObservableList<RoomItemModel> = FXCollections.observableArrayList()

    fun refreshRooms() {
        rooms.setAll(RoomService.getAll().map {
            RoomItemModel(it.id, it.seatCount)
        })
    }

    fun handleDeleteRoom(roomId: Int) {
        rooms.removeIf { it.id == roomId }
        RoomService.delete(roomId)
    }

    fun handleAdd(seatCount: Int) = RoomService.add(seatCount).let {
        rooms.add(RoomItemModel(it.id, it.seatCount))
    }

    fun handleEdit(oldRoom: RoomItemModel, newRoom: RoomItemModel) {
        rooms.setObject(oldRoom, newRoom)
        RoomService.update(newRoom.toRoom())
    }
}