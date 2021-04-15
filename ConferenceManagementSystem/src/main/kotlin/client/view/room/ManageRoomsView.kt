package client.view.room

import client.controller.room.ManageRoomsController
import client.model.RoomItemModel
import client.view.component.setNode
import client.view.conference.ConferenceListView
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class ManageRoomsView : View(APPLICATION_TITLE) {
    private val controller by inject<ManageRoomsController>()

    override fun onDock() {
        super.onDock()
        controller.refreshRooms()
    }

    override val root = vbox(alignment = Pos.CENTER) {
        paddingAll = 32.0

        vbox(32.0) {


            text("Manage Rooms") {
                font = Font(24.0)
            }


            tableview(controller.model.rooms) {
                maxWidth = 256.0
                maxHeight = 128.0
                readonlyColumn("Seats", RoomItemModel::seats) {
                    minWidth = 120.0
                    cellFormat {
                        alignment = Pos.CENTER
                        text = it.toString()
                    }
                }
                readonlyColumn("Actions", RoomItemModel::id) {
                    minWidth = 128.0
                    setNode {
                        val id = item
                        val room = controller.model.rooms.find { it.id == id }
                        button("Edit") {
                            action {
                                room?.let {
                                    manageRoomsDialog(id, room.seats) {
                                        controller.handleEdit(room, RoomItemModel(id, it))
                                    }
                                }
                            }
                        }
                        button("Delete") {
                            action {
                                alert(
                                    type = Alert.AlertType.CONFIRMATION,
                                    header = "Delete Room",
                                    content = "Delete room $item",
                                    actionFn = { buttonType ->
                                        if (buttonType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            controller.handleDeleteRoom(item)
                                        }
                                    })
                            }
                        }
                    }
                }
            }
            hbox(16.0, Pos.CENTER_RIGHT) {
                button("Back") {
                    action { switchTo(ConferenceListView::class) }
                }
                button("Add room") {
                    action {
                        manageRoomsDialog {
                            controller.handleAdd(it)
                        }
                    }
                }
            }
        }
    }
}