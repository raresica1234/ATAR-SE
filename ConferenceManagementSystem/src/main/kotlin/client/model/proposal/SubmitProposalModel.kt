package client.model.proposal

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import server.domain.Conference
import server.domain.Proposal
import utils.getOrEmpty

data class SubmitProposalModel(
    val conference: SimpleObjectProperty<Conference> = SimpleObjectProperty(),
    val name: SimpleStringProperty = SimpleStringProperty(),
    val topics: SimpleStringProperty = SimpleStringProperty(),
    val keywords: SimpleStringProperty = SimpleStringProperty(),
    val authors: SimpleStringProperty = SimpleStringProperty(),
    val abstractPaper: SimpleStringProperty = SimpleStringProperty(),
    val fullPaperLocation: SimpleStringProperty = SimpleStringProperty(),
    val fullPaperName: SimpleStringProperty = SimpleStringProperty("None selected")
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
