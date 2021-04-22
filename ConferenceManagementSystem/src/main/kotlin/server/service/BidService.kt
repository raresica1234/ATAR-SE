package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import server.bids
import server.database

class BidService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.bids.filter { it.proposalId.eq(proposalId) }.toList()

        fun getAllByPcMember(pcMemberId: Int) = database.bids.filter { it.pcMemberId eq pcMemberId }.toList()

        fun getAllApprovedByPcMember(pcMemberId: Int) = database.bids.filter {
            it.pcMemberId eq pcMemberId and it.approved
        }.toList()
    }
}