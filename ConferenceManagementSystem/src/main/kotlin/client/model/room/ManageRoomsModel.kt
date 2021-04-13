package client.model.room

import client.model.RoomItemModel
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

data class ManageRoomsModel(
    val rooms: ObservableList<RoomItemModel> = FXCollections.observableArrayList(),
    val seatCount: SimpleIntegerProperty = SimpleIntegerProperty()
)