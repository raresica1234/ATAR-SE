package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.count
import org.ktorm.entity.filter
import org.ktorm.entity.removeIf
import org.ktorm.entity.toList
import server.database
import server.domain.ApprovalStatus
import server.domain.Review
import server.reviews
import utils.hasSameSign

class ReviewService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.reviews.filter { it.proposalId.eq(proposalId) }.toList()

        fun remove(proposalId: Int, pcMemberId: Int) = database.reviews.removeIf {
            (it.proposalId eq proposalId) and (it.pcMemberId eq pcMemberId)
        }

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
    }
}