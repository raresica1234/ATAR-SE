package client.model.review

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import server.domain.Proposal
import server.domain.Review
import tornadofx.observableListOf

data class BidItemModel(
    val proposalId: Int,
    val pcMemberId: Int,
    val pcMember: String,
    val type: String,
    val approved: SimpleBooleanProperty
) {
    override fun toString() = "$pcMember -> $type"
}

data class ManageReviewsModel(
    val proposal: SimpleObjectProperty<Proposal> = SimpleObjectProperty(),
    val bids: ObservableList<BidItemModel> = observableListOf(),
    val reviews: ObservableList<Review> = observableListOf(),
    val isRevaluation: SimpleBooleanProperty = SimpleBooleanProperty(false)
)