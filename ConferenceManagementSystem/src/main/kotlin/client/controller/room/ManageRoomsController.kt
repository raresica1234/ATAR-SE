package client.controller.room

import client.model.RoomItemModel
import client.model.room.ManageRoomsModel
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import org.ktorm.dsl.eq
import org.ktorm.entity.removeIf
import server.database
import server.rooms
import server.service.RoomSerivce
import tornadofx.Controller
import tornadofx.alert

class ManageRoomsController : Controller() {
    val model = ManageRoomsModel()

    fun refreshRooms() {
        model.rooms.setAll(RoomSerivce.getAll().map {
            RoomItemModel(it.id, it.seatCount)
        })
    }

    fun handleDeleteRoomClick(room: Int) {
        alert(
            type = Alert.AlertType.CONFIRMATION,
            header = "Delete Room",
            content = "Delete room $room",
            actionFn = { btnType ->
                if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                    database.rooms.removeIf { it.id eq room }
                }
            }
        )
    }
}