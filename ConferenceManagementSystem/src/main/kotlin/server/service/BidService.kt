package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import server.bids
import server.database
import server.domain.Bid
import server.domain.BidType
import utils.LOG_EXCEPTIONS
import java.lang.Exception

class BidService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.bids.filter { it.proposalId.eq(proposalId) }.toList()

        fun getAllByPcMember(pcMemberId: Int) = database.bids.filter { it.pcMemberId eq pcMemberId }.toList()

        fun getAllApprovedByPcMember(pcMemberId: Int) = database.bids.filter {
            it.pcMemberId eq pcMemberId and it.approved
        }.toList()

        fun add(proposalId: Int, pcMemberId: Int, bidType: BidType, approved: Boolean): Bid? {
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
    }
}