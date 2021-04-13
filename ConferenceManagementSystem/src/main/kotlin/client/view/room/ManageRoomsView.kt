package client.view.room

import client.controller.room.ManageRoomsController
import client.model.RoomItemModel
import client.view.component.setNode
import client.view.conference.ConferenceListView
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.text.Font
import server.domain.Room
import server.service.RoomSerivce
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
                        button("Edit") {
                            action {
                                dialog {
                                    minWidth = 256.0
                                    minHeight = 128.0
                                    spacing = 16.0
                                    alignment = Pos.CENTER_LEFT
                                    label("New seats number for room $item: ")
                                    controller.model.seatCount.set(controller.model.rooms[item].seats)
                                    textfield(controller.model.seatCount)

                                    hbox(16.0, Pos.CENTER_RIGHT) {
                                        button("Save") {
                                            action {
                                                controller.model.rooms[item].seats = controller.model.seatCount.get()
                                                close()
                                            }
                                        }
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
                                    actionFn = { btnType ->
                                        if (btnType.buttonData == ButtonBar.ButtonData.OK_DONE) {
                                            controller.handleDeleteRoomClick(item)
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
                        dialog {
                            minWidth = 256.0
                            minHeight = 128.0
                            spacing = 16.0
                            alignment = Pos.CENTER_LEFT
                            label("Number of seats: ")
                            textfield(controller.model.seatCount)

                            hbox(16.0, Pos.CENTER_RIGHT) {
                                button("Add") {
                                    action {
                                        controller.handleAddRoomClick()
                                        close()
                                    }
                                }

                                button("Cancel") {
                                    action {
                                        close()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}