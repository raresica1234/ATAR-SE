package client.model.proposal

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import server.domain.ApprovalStatus
import server.domain.Conference
import server.domain.User
import server.service.ProposalWithReviews

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
    val presentationLocation: SimpleStringProperty = SimpleStringProperty(),
    val presentationName: SimpleStringProperty = SimpleStringProperty("None selected"),
    val statusText: SimpleStringProperty = SimpleStringProperty(),
    val recommendation: SimpleStringProperty = SimpleStringProperty(),
    val status: SimpleObjectProperty<ApprovalStatus> = SimpleObjectProperty()
) {
    fun setProposal(proposalWithReviews: ProposalWithReviews, authors: List<User>) = with(proposalWithReviews) {
        id = proposal.id
        name.set(proposal.name)
        topics.set(proposal.topics.replace("\n", ", "))
        keywords.set(proposal.keywords.replace("\n", ", "))
        this@ViewProposalModel.authors.set(authors.joinToString { it.fullName.ifBlank { it.email } })
        abstractPaper.set(proposal.abstractPaper)
        fullPaperLocation.set(proposal.fullPaper)
        presentationLocation.set(proposal.presentation)
        status.set(proposal.status)
        statusText.set(proposal.status.value)
        recommendation.set(reviews.joinToString("\n\n") { it.recommendation }.ifBlank { "-" })
    }
}