package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.or
import org.ktorm.entity.*
import server.bids
import server.database
import server.domain.Bid
import server.domain.BidType
import server.domain.User
import utils.LOG_EXCEPTIONS

data class BidWithPcMember(val bid: Bid, val pcMember: User)

class BidService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.bids.filter { it.proposalId.eq(proposalId) }.toList()

        fun getAllByPcMember(pcMemberId: Int) = database.bids.filter { it.pcMemberId eq pcMemberId }.toList()

        fun getAllApprovedByPcMember(pcMemberId: Int) = database.bids.filter {
            it.pcMemberId eq pcMemberId and it.approved
        }.toList()

        fun add(proposalId: Int, pcMemberId: Int, bidType: BidType, approved: Boolean = false): Bid? {
            val bid = Bid {
                this.proposalId = proposalId
                this.pcMemberId = pcMemberId
                this.bidType = bidType
                this.approved = approved
            }

            try {
                database.bids.add(bid)
            } catch (exception: Exception) {
                if (LOG_EXCEPTIONS) println("Exception in BidService.add($bid): $exception")
                return null
            }

            return bid
        }

        fun getAllWillingToReviewForProposal(proposalId: Int) = database.bids.filter {
            (it.proposalId eq proposalId) and
                    ((it.bidType eq BidType.PLEASED_TO_REVIEW) or (it.bidType eq BidType.COULD_REVIEW))
        }.sortedByDescending { it.bidType }
            .mapNotNull { BidWithPcMember(it, UserService.get(it.pcMemberId) ?: return@mapNotNull null) }

        fun updateApproval(proposalId: Int, pcMemberId: Int, approved: Boolean) {
            val bid = Bid {
                this.proposalId = proposalId
                this.pcMemberId = pcMemberId
                this.approved = approved
            }

            database.bids.update(bid)
        }
    }
}