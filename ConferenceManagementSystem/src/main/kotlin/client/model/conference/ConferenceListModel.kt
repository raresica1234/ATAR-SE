package client.model.conference

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

data class ConferenceListModel(
    val allConferences: ObservableList<ConferenceListItemModel> = FXCollections.observableArrayList(),
    val activeConferences: ObservableList<ConferenceListItemModel> = FXCollections.observableArrayList(),
    val participatingConferences: ObservableList<ConferenceListItemModel> = FXCollections.observableArrayList(),
    val search: SimpleStringProperty = SimpleStringProperty(),
    val selectedConference: SimpleObjectProperty<ConferenceListItemModel> = SimpleObjectProperty<ConferenceListItemModel>(),
) {
    fun getInitialActiveConferences() = allConferences.take(5)
    fun getInitialParticipatingConferences() = allConferences.takeLast(2)
}