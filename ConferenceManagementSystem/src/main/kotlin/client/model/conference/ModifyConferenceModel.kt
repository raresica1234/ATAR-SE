package client.model.conference

import client.model.ProposalItemModel
import client.model.RoomItemModel
import client.model.SelectedUserItemModel
import client.model.UserItemModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import server.domain.Conference
import utils.clear
import utils.getOrEmpty
import java.time.LocalDate

data class ModifyConferenceSourceModel(
    val chairs: ObservableList<UserItemModel> = FXCollections.observableArrayList(),
    val committees: ObservableList<SelectedUserItemModel> = FXCollections.observableArrayList(),
    val rooms: ObservableList<RoomItemModel> = FXCollections.observableArrayList()
)

data class ModifyConferenceSectionModel(
    val id: SimpleIntegerProperty = SimpleIntegerProperty(),
    val name: SimpleStringProperty = SimpleStringProperty(),
    val selectedRoom: SimpleObjectProperty<RoomItemModel> = SimpleObjectProperty(),
    val startDate: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val endDate: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val proposals: ObservableList<ProposalItemModel> = FXCollections.observableArrayList()
) {
    override fun toString() = name.getOrEmpty()
}

data class ModifyConferenceModel(
    val id: SimpleIntegerProperty = SimpleIntegerProperty(),
    val name: SimpleStringProperty = SimpleStringProperty(),
    val abstractDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val paperDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val biddingDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val reviewDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val sources: ModifyConferenceSourceModel = ModifyConferenceSourceModel(),
    val selectedChair: SimpleObjectProperty<UserItemModel> = SimpleObjectProperty(),
    val searchedCommittees: ObservableList<SelectedUserItemModel> = FXCollections.observableArrayList(),
    val sections: ObservableList<ModifyConferenceSectionModel> = FXCollections.observableArrayList(),
    val selectedSection: SimpleObjectProperty<ModifyConferenceSectionModel> = SimpleObjectProperty(),
    val selectedSectionDetails: ModifyConferenceSectionModel = ModifyConferenceSectionModel(),
    val search: SimpleStringProperty = SimpleStringProperty(),
    val isLoading: SimpleBooleanProperty = SimpleBooleanProperty(true)
) {
    fun setConference(conference: Conference) {
        name.set(conference.name)
        abstractDeadline.set(conference.abstractDeadline)
        paperDeadline.set(conference.paperDeadline)
        biddingDeadline.set(conference.biddingDeadline)
        reviewDeadline.set(conference.reviewDeadline)
    }

    fun toConference() = Conference {
        id = this@ModifyConferenceModel.id.get()
        name = this@ModifyConferenceModel.name.getOrEmpty()
        abstractDeadline = this@ModifyConferenceModel.abstractDeadline.get()
        paperDeadline = this@ModifyConferenceModel.paperDeadline.get()
        biddingDeadline = this@ModifyConferenceModel.biddingDeadline.get()
        reviewDeadline = this@ModifyConferenceModel.reviewDeadline.get()
    }

    fun reset() = run {
        isLoading.set(true)
        search.clear()
        selectedChair.clear()
    }
}