package client.controller.review

import client.model.review.BidItemModel
import client.model.review.ManageReviewsModel
import javafx.beans.property.SimpleBooleanProperty
import server.domain.ApprovalStatus
import server.domain.Bid
import server.domain.Proposal
import server.service.BidService
import server.service.ProposalService
import tornadofx.Controller
import tornadofx.onChange

class ManageReviewsController : Controller() {
    val model = ManageReviewsModel()

    fun onParamsSet(proposalId: Int) {
        fetchData(proposalId)
    }

    private fun fetchData(proposalId: Int) {
        data class FetchData(val proposal: Proposal?, val bids: List<BidItemModel>)

        runAsync {
            // TODO: Make another service function to get with reviews and replace the current one
            val proposal = ProposalService.get(proposalId)

            val bids = BidService.getAllWillingToReviewForProposal(proposalId).map { bidWithPcMember ->
                with(bidWithPcMember) {
                    BidItemModel(
                        bid.proposalId,
                        bid.pcMemberId,
                        "${pcMember.fullName} - ${pcMember.email}",
                        bid.bidType.value,
                        SimpleBooleanProperty(bid.approved).apply {
                            onChange { handleApprovalChange(proposal, bid, it) }
                        }
                    )
                }
            }

            FetchData(proposal, bids)
        } ui {
            model.proposal.set(it.proposal)
            model.bids.setAll(it.bids)
        }
    }

    private fun handleApprovalChange(proposal: Proposal?, bid: Bid, approved: Boolean) {
        BidService.updateApproval(bid.proposalId, bid.pcMemberId, approved)
        proposal?.let {
            if (approved && (it.status != ApprovalStatus.IN_CONFLICT || it.status != ApprovalStatus.IN_REVIEW)) {
                ProposalService.updateStatus(it.id, ApprovalStatus.IN_REVIEW)
            }
        }
    }
}