package client.controller.review

import client.model.review.BidItemModel
import client.model.review.ManageReviewsModel
import javafx.beans.property.SimpleBooleanProperty
import server.domain.*
import server.service.BidService
import server.service.ConferenceService
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
        data class FetchData(
            val proposal: Proposal? = null,
            val bids: List<BidItemModel> = emptyList(),
            val reviews: List<Review> = emptyList(),
            val conference: Conference? = null
        )

        val isRevaluation = model.isRevaluation.get()

        runAsync {
            val proposalWithReviews = ProposalService.getWithReviews(proposalId)
                ?: return@runAsync FetchData()

            val proposal = proposalWithReviews.proposal

            val bids = BidService.getAllWillingToReviewForProposal(proposalId, isRevaluation).map { bidWithPcMember ->
                with(bidWithPcMember) {
                    BidItemModel(
                        bid.proposalId,
                        bid.pcMemberId,
                        "${pcMember.fullName} - ${pcMember.email}",
                        bid.bidType.value,
                        SimpleBooleanProperty(bid.approved).apply {
                            onChange { handleApprovalChange(proposal, bid, isRevaluation, it) }
                        }
                    )
                }
            }

            val conference = ConferenceService.get(proposal.conferenceId)

            FetchData(proposalWithReviews.proposal, bids, proposalWithReviews.reviews, conference)
        } ui {
            model.proposal.set(it.proposal)
            model.bids.setAll(it.bids)
            model.reviews.setAll(it.reviews)
            model.maximumReviewers.set(it.conference?.reviewerCount ?: 0)
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