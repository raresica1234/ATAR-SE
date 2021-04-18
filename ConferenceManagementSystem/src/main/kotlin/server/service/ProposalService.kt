package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.find
import org.ktorm.entity.toList
import server.database
import server.domain.Proposal
import server.domain.ProposalAuthor
import server.domain.RoleType
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
                    RoleService.add(it.id, proposal.conferenceId, RoleType.AUTHOR)
                }
        }

        fun getAllBySectionId(sectionId: Int) = database.proposals
            .filter { it.sectionId.eq(sectionId) }
            .toList()

        fun getAuthorsForProposal(proposalId: Int) = database.proposalAuthors
            .filter { it.proposalId.eq(proposalId) }
            .mapNotNull { UserService.get(it.authorId) }
    }
}
