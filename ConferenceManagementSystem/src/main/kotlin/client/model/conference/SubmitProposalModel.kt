package client.model.conference

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import server.domain.Conference
import server.domain.Proposal
import utils.getOrEmpty

data class SubmitProposalModel(
    var conference: SimpleObjectProperty<Conference> = SimpleObjectProperty(),
    var name: SimpleStringProperty = SimpleStringProperty(),
    var topics: SimpleStringProperty = SimpleStringProperty(),
    var keywords: SimpleStringProperty = SimpleStringProperty(),
    var authors: SimpleStringProperty = SimpleStringProperty(),
    var abstractPaper: SimpleStringProperty = SimpleStringProperty(),
    var fullPaperLocation: SimpleStringProperty = SimpleStringProperty(),
    var fullPaperName: SimpleStringProperty = SimpleStringProperty("None selected")
) {
    fun toProposal() = Proposal {
        name = this@SubmitProposalModel.name.get()
        abstractPaper = this@SubmitProposalModel.abstractPaper.get()
        fullPaper = this@SubmitProposalModel.fullPaperLocation.getOrEmpty()
        keywords = this@SubmitProposalModel.keywords.get()
        topics = this@SubmitProposalModel.topics.get()
        conferenceId = this@SubmitProposalModel.conference.get().id
    }
}
