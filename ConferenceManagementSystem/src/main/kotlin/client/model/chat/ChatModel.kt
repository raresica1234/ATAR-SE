package client.model.chat

import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import server.domain.Chat
import tornadofx.observableListOf

data class ChatModel(
    val messages: ObservableList<Chat> = observableListOf(),
    val message: SimpleStringProperty = SimpleStringProperty()
)