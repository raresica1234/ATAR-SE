package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.*
import server.database
import server.domain.Proposal
import server.domain.ProposalAuthor
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

            authors.mapNotNull { email -> database.users.find { it.email.eq(email) } }
                .forEach {
                    database.proposalAuthors.add(ProposalAuthor {
                        proposalId = proposal.id
                        authorId = it.id
                    })
                }
        }
    }
}
