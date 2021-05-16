package client.controller.review

import client.model.review.BidItemModel
import client.model.review.ManageReviewsModel
import javafx.beans.property.SimpleBooleanProperty
import server.domain.ApprovalStatus
import server.domain.Bid
import server.domain.Proposal
import server.domain.Review
import server.service.BidService
import server.service.ProposalService
import server.service.ReviewService
import tornadofx.Controller
import tornadofx.onChange

class ManageReviewsController : Controller() {
    val model = ManageReviewsModel()

    fun onParamsSet(proposalId: Int, isRevaluation: Boolean) {
        model.isRevaluation.set(isRevaluation)
        fetchData(proposalId)
    }

    private fun fetchData(proposalId: Int) {
        data class FetchData(val proposal: Proposal?, val bids: List<BidItemModel>, val reviews: List<Review>)

        val isRevaluation = model.isRevaluation.get()

        runAsync {
            val proposalWithReviews = ProposalService.getWithReviews(proposalId)
                ?: return@runAsync FetchData(null, emptyList(), emptyList())

            val bids = BidService.getAllWillingToReviewForProposal(proposalId, isRevaluation).map { bidWithPcMember ->
                with(bidWithPcMember) {
                    BidItemModel(
                        bid.proposalId,
                        bid.pcMemberId,
                        "${pcMember.fullName} - ${pcMember.email}",
                        bid.bidType.value,
                        SimpleBooleanProperty(bid.approved).apply {
                            onChange { handleApprovalChange(proposalWithReviews.proposal, bid, isRevaluation, it) }
                        }
                    )
                }
            }

            FetchData(proposalWithReviews.proposal, bids, proposalWithReviews.reviews)
        } ui {
            model.proposal.set(it.proposal)
            model.bids.setAll(it.bids)
            model.reviews.setAll(it.reviews)
        }
    }

    private fun handleApprovalChange(proposal: Proposal?, bid: Bid, isRevaluation: Boolean, approved: Boolean) {
        BidService.updateApproval(bid.proposalId, bid.pcMemberId, approved)
        proposal?.let {
            if (isRevaluation) {
                return@let handleRevaluation(it)
            }
            if (approved && (it.status != ApprovalStatus.IN_CONFLICT || it.status != ApprovalStatus.IN_REVIEW)) {
                ProposalService.updateStatus(it.id, ApprovalStatus.IN_REVIEW)
            }
            if (!approved) {
                ReviewService.remove(it.id, bid.pcMemberId)
            }
        }
    }

    private fun handleRevaluation(proposal: Proposal) {
        if (proposal.status == ApprovalStatus.IN_REVALUATION) return

        ProposalService.setRevaluationStatus(proposal.id)
    }
}