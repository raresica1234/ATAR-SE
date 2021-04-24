package client.view.chat

import javafx.beans.value.ObservableStringValue
import javafx.geometry.Pos
import tornadofx.*
import utils.APPLICATION_TITLE

class ChatView : View(APPLICATION_TITLE) {

    override val root = vbox(64.0, Pos.CENTER) {
        minWidth = 528.0
        minHeight = 456.0

        vbox(16.0, Pos.CENTER) {

            listview<ObservableStringValue> {
                maxWidth = 480.0
                maxHeight = 280.0
            }

            textarea {
                maxWidth = 480.0
                maxHeight = 80.0
            }

            vbox(alignment = Pos.CENTER_RIGHT) {
                maxWidth = 480.0

                button("Send")
            }
        }
    }
}