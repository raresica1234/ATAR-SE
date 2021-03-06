package client.model.conference

import javafx.beans.property.SimpleBooleanProperty
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
    val selectedConference: SimpleObjectProperty<ConferenceListItemModel> = SimpleObjectProperty<ConferenceListItemModel>(),
    val isLoading: SimpleBooleanProperty = SimpleBooleanProperty(true)
) {
    fun clear() {
        selectedConference.set(null)
        search.set(null)
        isLoading.set(true)

        initialActiveConferences.clear()
        initialParticipatingConferences.clear()
    }

    fun getConferenceId() = selectedConference.get().id

    fun getConference() = selectedConference.get().conference
}