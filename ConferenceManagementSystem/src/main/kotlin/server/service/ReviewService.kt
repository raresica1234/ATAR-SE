package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import server.database
import server.reviews

class ReviewService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.reviews.filter { it.proposalId.eq(proposalId) }.toList()
    }
}