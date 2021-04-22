package client.model.proposal

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import server.domain.Conference

class ViewProposalModel(
    var id: Int = 0,
    val conference: SimpleObjectProperty<Conference> = SimpleObjectProperty(),
    val name: SimpleStringProperty = SimpleStringProperty(),
    val topics: SimpleStringProperty = SimpleStringProperty(),
    val keywords: SimpleStringProperty = SimpleStringProperty(),
    val authors: SimpleStringProperty = SimpleStringProperty(),
    val abstractPaper: SimpleStringProperty = SimpleStringProperty(),
    val fullPaperLocation: SimpleStringProperty = SimpleStringProperty(),
    val fullPaperName: SimpleStringProperty = SimpleStringProperty("None selected"),
    val status: SimpleStringProperty = SimpleStringProperty(),
    val recommendation: SimpleStringProperty = SimpleStringProperty()
)