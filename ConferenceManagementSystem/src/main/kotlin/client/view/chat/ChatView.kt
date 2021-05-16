package client.view.chat

import client.state.userState
import client.view.component.renderItem
import client.view.review.ReviewPaperView
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import server.domain.Chat
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.format
import utils.switchTo

class ChatView : View(APPLICATION_TITLE) {

    override val root = vbox(64.0, Pos.CENTER) {
        minWidth = 528.0
        minHeight = 456.0

        vbox(16.0, Pos.CENTER) {

            listview(observableListOf<Chat>()) {
                maxWidth = 480.0
                maxHeight = 280.0
                spacing = 16.0

                renderItem {
                    vbox(8.0, if (it.userId == userState.user.id) Pos.CENTER_RIGHT else Pos.BOTTOM_LEFT) {
                        hbox(16.0) {
                            text("User") {
                                font = Font(14.0)

                                style { fontWeight = FontWeight.BOLD }
                            }
                            label(it.timestamp.format())
                        }
                        textflow {
                            maxWidth = 384.0

                            text(it.message)
                        }
                    }
                }
            }

            textarea {
                maxWidth = 480.0
                maxHeight = 80.0
            }

            hbox(8.0, alignment = Pos.CENTER_RIGHT) {
                maxWidth = 480.0

                button("Back") {
                    action { switchTo(ReviewPaperView::class) }
                }
                button("Send") {
                    action {
                        println("Send message")
                    }
                }
            }
        }
    }
}