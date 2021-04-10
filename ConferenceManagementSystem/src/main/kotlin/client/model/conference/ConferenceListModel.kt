package client.model.conference

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import server.domain.Role

data class ConferenceListModel(
    val roles: ObservableList<Role> = FXCollections.observableArrayList(),
    val initialActiveConferences: ObservableList<ConferenceListItemModel> = FXCollections.observableArrayList(),
    val initialParticipatingConferences: ObservableList<ConferenceListItemModel> = FXCollections.observableArrayList(),
    val activeConferences: ObservableList<ConferenceListItemModel> = FXCollections.observableArrayList(),
    val participatingConferences: ObservableList<ConferenceListItemModel> = FXCollections.observableArrayList(),
    val search: SimpleStringProperty = SimpleStringProperty(),
    val selectedConference: SimpleObjectProperty<ConferenceListItemModel> = SimpleObjectProperty<ConferenceListItemModel>()
) {
    fun clear() {
        selectedConference.set(null)
        search.set(null)

        initialActiveConferences.clear()
        initialParticipatingConferences.clear()
    }
}