package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.*
import server.database
import server.domain.Proposal
import server.domain.ProposalAuthor
import server.domain.User
import server.proposalAuthors
import server.proposals
import server.users

class ProposalService {
    companion object {
        fun getAllByConferenceId(conferenceId: Int) = database.proposals
            .filter { it.conferenceId.eq(conferenceId) }
            .toList()

        fun submitProposal(proposal: Proposal, authors: List<String>) {
            database.proposals.add(proposal)

            var users = MutableList(0) { User {} }

            authors.forEach { email -> database.users.find { it.email.eq(email) }?.let { users.add(it) } }

            users.forEach {
                database.proposalAuthors.add(ProposalAuthor {
                    proposalId = proposal.id
                    authorId = it.id
                })
            }
        }
    }
}
