package client.controller.review

import client.model.DetailedProposalItemModel
import client.model.review.ReviewPaperModel
import client.state.userState
import server.domain.Review
import server.service.ReviewService
import tornadofx.Controller
import utils.ValidationException

class ReviewPaperController : Controller() {
    val model = ReviewPaperModel()

    fun onParamsSet(proposal: DetailedProposalItemModel) {
        model.proposal.set(proposal)
    }

    fun openPaper() = hostServices.showDocument(model.proposal.get().fullPaper)

    fun submitReview() = try {
        validateFields()

        ReviewService.add(Review {
            proposalId = model.proposal.get().id
            pcMemberId = userState.user.id
            reviewType = model.selectedReviewType.get()
            recommendation = model.recommendation.get().orEmpty()
        })
        true
    } catch (exception: ValidationException) {
        false
    }

    private fun validateFields() {
        if (model.selectedReviewType.get() == null) {
            throw ValidationException(
                "Missing review statement",
                "The review statement has to be specified in order to submit a review."
            )
        }
    }
}