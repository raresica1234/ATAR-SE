package server.domain

import org.ktorm.entity.Entity

interface ProposalAuthor : Entity<ProposalAuthor> {
    companion object : Entity.Factory<ProposalAuthor>()

    var proposalId: Int
    var authorId: Int
}