package client.controller.room

import client.model.RoomItemModel
import client.model.room.ManageRoomsModel
import org.ktorm.dsl.eq
import org.ktorm.entity.removeIf
import server.database
import server.domain.Room
import server.rooms
import server.service.RoomSerivce
import tornadofx.Controller

class ManageRoomsController : Controller() {
    val model = ManageRoomsModel()

    fun refreshRooms() {
        model.rooms.setAll(RoomSerivce.getAll().map {
            RoomItemModel(it.id, it.seatCount)
        })
    }

    fun handleDeleteRoomClick(room: Int) {
        RoomSerivce.remove(room)
    }

    fun handleAddRoomClick() {
        val seatCount = model.seatCount.get()
        val newRoom = Room{this.seatCount = seatCount}
        RoomSerivce.add(newRoom)
        val roomItemModel = RoomItemModel()
        roomItemModel.seats = seatCount
        model.rooms.add(roomItemModel)
    }
}