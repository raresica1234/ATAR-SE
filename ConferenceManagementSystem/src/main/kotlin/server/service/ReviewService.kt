package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.*
import server.database
import server.domain.ApprovalStatus
import server.domain.Review
import server.domain.ReviewType
import server.reviews
import utils.hasSameSign

class ReviewService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.reviews.filter { it.proposalId.eq(proposalId) }.toList()

        fun remove(proposalId: Int, pcMemberId: Int) = database.reviews.removeIf {
            (it.proposalId eq proposalId) and (it.pcMemberId eq pcMemberId)
        }

        fun remove(proposalId: Int) = database.reviews.removeIf { it.proposalId eq proposalId }

        fun add(review: Review): Review {
            database.reviews.add(review)

            val proposalId = review.proposalId
            val proposalReviews = getAllByProposalId(proposalId)

            if (ProposalService.isProposalInConflict(proposalId)) {
                return review
            }

            if (proposalReviews.any { !it.reviewValue.hasSameSign(review.reviewValue) }) {
                ProposalService.updateStatus(proposalId, ApprovalStatus.IN_CONFLICT)
                return review
            }

            val reviews = getAllByProposalId(proposalId)

            if (BidService.getCountForProposal(proposalId) == reviews.size) {
                val status = if (reviews.sumBy { it.reviewValue } > 0) {
                    ApprovalStatus.APPROVED
                } else {
                    ApprovalStatus.REJECTED
                }

                ProposalService.updateStatus(proposalId, status)
            }

            return review
        }

        fun getAllByPcMember(pcMemberId: Int) = database.reviews.filter { it.pcMemberId eq pcMemberId }.toList()

        fun invalidate(proposalId: Int) = database.reviews.filter { it.proposalId eq proposalId }.forEach {
            it.reviewType = ReviewType.INVALID
        }
    }
}