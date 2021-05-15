package client.model.conference

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import tornadofx.observableListOf

data class SectionModel(val id: Int, val name: String) {
    override fun toString() = name
}

data class SectionSelectionModel(
    val sections: ObservableList<SectionModel> = observableListOf(),
    val selectedSection: SimpleObjectProperty<SectionModel> = SimpleObjectProperty(),
    val conferenceId: SimpleIntegerProperty = SimpleIntegerProperty()
)