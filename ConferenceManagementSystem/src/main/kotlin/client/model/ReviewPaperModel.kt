package client.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import server.domain.ReviewType

class ReviewPaperModel(
    val proposal: SimpleObjectProperty<DetailedProposalItemModel> = SimpleObjectProperty(),
    val selectedReviewType: SimpleObjectProperty<ReviewType> = SimpleObjectProperty(),
    val recommendation: SimpleStringProperty = SimpleStringProperty()
)