package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.isNull
import org.ktorm.dsl.or
import org.ktorm.entity.*
import server.database
import server.domain.*
import server.proposalAuthors
import server.proposals
import server.users

data class ProposalWithAuthors(val proposal: Proposal, val authors: List<User>)

data class ProposalWithBidsAndReviews(val proposal: Proposal, val bids: List<Bid>, val reviews: List<Review>)

class ProposalService {
    companion object {
        fun getAllByConferenceId(conferenceId: Int) = database.proposals
            .filter { it.conferenceId.eq(conferenceId) }
            .toList()

        fun submitProposal(proposal: Proposal, authors: List<String>) {
            proposal.status = ApprovalStatus.TO_BE_REVIEWED
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

        fun getProposalByConferenceAndAuthorId(conferenceId: Int, authorId: Int): ProposalWithBidsAndReviews? {
            val proposalAuthors = database.proposalAuthors.filter { it.authorId.eq(authorId) }.toList()
            val proposals = database.proposals.filter { it.conferenceId.eq(conferenceId) }.toList()

            val proposal = proposals.find { proposal ->
                proposalAuthors.any { it.proposalId == proposal.id }
            } ?: return null

            return ProposalWithBidsAndReviews(
                proposal,
                BidService.getAllByProposalId(proposal.id),
                ReviewService.getAllByProposalId(proposal.id)
            )
        }

        fun getProposalAuthors(proposalId: Int) = database.proposalAuthors
            .filter { it.proposalId.eq(proposalId) }
            .mapNotNull { UserService.get(it.authorId) }

        fun updateFullPaper(proposalId: Int, fullPaper: String) = database.proposals.update(Proposal {
            id = proposalId
            this.fullPaper = fullPaper
        })

        fun getAllBySectionId(sectionId: Int) = database.proposals
            .filter { it.sectionId.eq(sectionId) }
            .toList()

        fun getAllUnassociatedForConference(conferenceId: Int) = database.proposals.filter {
            (it.conferenceId eq conferenceId) and
                    (it.status eq ApprovalStatus.APPROVED) and
                    ((it.sectionId eq 0) or it.sectionId.isNull())
        }.toList()

        fun setToSection(proposalId: Int, sectionId: Int = 0) = database.proposals.update(Proposal {
            id = proposalId
            this.sectionId = sectionId
        })

        fun getWithAuthors(proposalId: Int) = database.proposals.find { it.id eq proposalId }?.let {
            ProposalWithAuthors(it, getAuthorsForProposal(proposalId))
        }
    }
}
