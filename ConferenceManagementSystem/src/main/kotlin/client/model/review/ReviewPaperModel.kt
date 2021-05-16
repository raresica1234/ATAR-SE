package client.model.review

import client.model.DetailedProposalItemModel
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import server.domain.ReviewType

class ReviewPaperModel(
    val proposal: SimpleObjectProperty<DetailedProposalItemModel> = SimpleObjectProperty(),
    val selectedReviewType: SimpleObjectProperty<ReviewType> = SimpleObjectProperty(),
    val recommendation: SimpleStringProperty = SimpleStringProperty()
) {
    fun set(proposal: DetailedProposalItemModel? = null) {
        this.proposal.set(proposal)
        selectedReviewType.set(null)
        recommendation.set(null)
    }
}