package client.model.conference

import client.model.UserItemModel
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import server.domain.Conference
import utils.clear
import java.time.LocalDate

data class CreateConferenceModel(
    val name: SimpleStringProperty = SimpleStringProperty(),
    val chair: SimpleObjectProperty<UserItemModel> = SimpleObjectProperty(),
    val chairs: ObservableList<UserItemModel> = FXCollections.observableArrayList(),
    val abstractDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val paperDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val biddingDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty(),
    val reviewDeadline: SimpleObjectProperty<LocalDate> = SimpleObjectProperty()
) {
    fun toConference() = Conference {
        name = this@CreateConferenceModel.name.get()
        abstractDeadline = this@CreateConferenceModel.abstractDeadline.get()
        paperDeadline = this@CreateConferenceModel.paperDeadline.get()
        biddingDeadline = this@CreateConferenceModel.biddingDeadline.get()
        reviewDeadline = this@CreateConferenceModel.reviewDeadline.get()
    }

    fun clear() {
        name.clear()
        chair.clear()
        abstractDeadline.clear()
        paperDeadline.clear()
        biddingDeadline.clear()
        reviewDeadline.clear()
    }

    fun getChairId() = chair.get().id
}

