package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.removeIf
import org.ktorm.entity.toList
import server.database
import server.reviews

class ReviewService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.reviews.filter { it.proposalId.eq(proposalId) }.toList()

        fun remove(proposalId: Int, pcMemberId: Int) = database.reviews.removeIf {
            (it.proposalId eq proposalId) and (it.pcMemberId eq pcMemberId)
        }
    }
}