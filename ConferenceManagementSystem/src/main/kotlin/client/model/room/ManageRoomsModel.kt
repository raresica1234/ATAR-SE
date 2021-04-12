package client.model.room

import client.model.RoomItemModel
import javafx.collections.FXCollections
import javafx.collections.ObservableList

data class ManageRoomsModel(val rooms: ObservableList<RoomItemModel> = FXCollections.observableArrayList())