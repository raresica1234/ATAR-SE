package client.view.room

import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import tornadofx.*


fun UIComponent.manageRoomsDialog(roomId: Int = 0, initialSeats: Int = 0, onSave: (Int) -> Unit = {}) = dialog {
    minWidth = 256.0
    minHeight = 128.0
    spacing = 16.0
    alignment = Pos.CENTER_LEFT
    val numberOfSeats = SimpleIntegerProperty(initialSeats)

    if (roomId == 0) {
        label("Seats number for new room: ")
    } else {
        label("New seats number for room $roomId: ")
    }
    textfield(numberOfSeats)

    hbox(16.0, Pos.CENTER_RIGHT) {
        button("Close") {
            action {
                close()
            }
        }
        button("Save") {
            action {
                onSave(numberOfSeats.get())
                close()
            }
        }
    }
}