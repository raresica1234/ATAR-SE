package client.view.chat

import client.controller.chat.ChatController
import client.state.userState
import client.view.ViewWithParams
import client.view.component.renderItem
import client.view.review.ReviewPaperView
import javafx.geometry.Pos
import javafx.scene.control.ListView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import server.domain.Chat
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.format
import utils.getNoSelectionMode
import utils.switchTo

class ChatView : ViewWithParams(APPLICATION_TITLE) {
    val controller by inject<ChatController>()

    override fun onParamSet() {
        controller.onParamsSet(getParam<Int>("proposalId") ?: 0)
    }

    override val root = vbox(32.0, Pos.CENTER) {
        minWidth = 528.0
        minHeight = 456.0
        paddingAll = 16.0

        vbox(16.0) {
            text("Chat") {
                font = Font(24.0)
            }

            listview(controller.model.messages) {
                maxHeight = 280.0
                selectionModel = getNoSelectionMode()
                focusModel = null

                controller.model.messages.onChange { scrollTo(it.list.size - 1) }
                renderChatItem()
            }

            textarea(controller.model.message) {
                maxHeight = 80.0
                isWrapText = true

                requestFocus()
            }

            hbox(16.0, alignment = Pos.CENTER_RIGHT) {
                button("Back") {
                    action { switchTo(ReviewPaperView::class) }
                }
                button("Send") {
                    action { controller.sendMessage() }
                    shortcut(KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHIFT_DOWN))
                }
            }
        }
    }

    private fun ListView<Chat>.renderChatItem() = renderItem {
        val isCurrentUser = it.userId == userState.user.id
        val alignment = if (isCurrentUser) Pos.CENTER_RIGHT else Pos.BOTTOM_LEFT
        val userName = it.user?.let { user -> user.fullName.ifBlank { user.email } } ?: "Unknown user"

        vbox(8.0, alignment) {
            hbox(16.0, alignment) {
                text(if (isCurrentUser) "" else userName) {
                    font = Font(14.0)

                    style { fontWeight = FontWeight.BOLD }
                }
                label(it.timestamp.format())
            }
            textflow {
                paddingBottom = 8.0
                textAlignment = if (isCurrentUser) TextAlignment.RIGHT else TextAlignment.LEFT

                text(it.message)
            }
        }
    }
}