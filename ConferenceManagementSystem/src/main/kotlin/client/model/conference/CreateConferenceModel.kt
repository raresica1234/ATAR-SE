package client.model.conference

import client.model.UserItemModel
import javafx.beans.Observable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.time.LocalDate

data class CreateConferenceModel(
    val name: SimpleStringProperty = SimpleStringProperty(),
    val chair: SimpleObjectProperty<UserItemModel> = SimpleObjectProperty(),
    val chairs: ObservableList<UserItemModel> = FXCollections.observableArrayList(),
    val abstractDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val paperDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val biddingDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val reviewDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty()
)

