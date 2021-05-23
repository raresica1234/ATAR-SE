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

data class ProposalWithReviews(val proposal: Proposal, val reviews: List<Review>)

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

        fun getProposalByConferenceAndAuthorId(conferenceId: Int, authorId: Int): ProposalWithReviews? {
            val proposalAuthors = database.proposalAuthors.filter { it.authorId.eq(authorId) }.toList()
            val proposals = database.proposals.filter { it.conferenceId.eq(conferenceId) }.toList()

            val proposal = proposals.find { proposal ->
                proposalAuthors.any { it.proposalId == proposal.id }
            } ?: return null

            return getWithReviewsForAuthor(proposal, authorId)
        }

        private fun getWithReviewsForAuthor(proposal: Proposal, authorId: Int?): ProposalWithReviews {
            if (authorId != null) {
                markNotificationAsRead(proposal.id, authorId)
            }

            return ProposalWithReviews(
                proposal,
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

        fun setToSection(proposalId: Int, sectionId: Int = 0) {
            database.proposals.update(Proposal {
                id = proposalId
                this.sectionId = sectionId
            })
            database.proposalAuthors.find { it.proposalId eq proposalId }?.let {
                if (sectionId == 0) {
                    ParticipantService.delete(sectionId, it.authorId)
                } else {
                    ParticipantService.addSpeaker(sectionId, it.authorId)
                }
            }
        }

        fun getWithAuthors(proposalId: Int) = get(proposalId)?.let {
            ProposalWithAuthors(it, getProposalAuthors(proposalId))
        }

        fun getAllWithAuthorsByConference(conferenceId: Int) = database.proposals
            .filter { it.conferenceId eq conferenceId }
            .mapNotNull { ProposalWithAuthors(it, getProposalAuthors(it.id)) }

        fun getAllInConflictWithAuthorsByConference(conferenceId: Int) = database.proposals
            .filter { (it.conferenceId eq conferenceId) and (it.status eq ApprovalStatus.IN_CONFLICT) }
            .mapNotNull { ProposalWithAuthors(it, getProposalAuthors(it.id)) }

        fun getAllForBiddingWithAuthors(conferenceId: Int, pcMemberId: Int): List<ProposalWithAuthors> {
            val madeBids = BidService.getAllByPcMember(pcMemberId)

            return database.proposals.filter {
                (it.conferenceId eq conferenceId) and
                        ((it.status eq ApprovalStatus.TO_BE_REVIEWED) or
                                (it.status eq ApprovalStatus.IN_REVIEW) or
                                (it.status eq ApprovalStatus.IN_DISCUSSION) or
                                (it.status eq ApprovalStatus.IN_REVALUATION))
            }.toList()
                .filter { proposal -> madeBids.none { it.proposalId == proposal.id } }
                .map {
                    ProposalWithAuthors(it, getProposalAuthors(it.id))
                }
        }

        fun getAllForReviewWithAuthors(conferenceId: Int, pcMemberId: Int): List<ProposalWithAuthors> {
            val approvedBids = BidService.getAllApprovedByPcMember(pcMemberId)
            val reviews = ReviewService.getAllByPcMember(pcMemberId)

            return database.proposals.filter {
                (it.conferenceId eq conferenceId) and
                        ((it.status eq ApprovalStatus.IN_REVIEW) or
                                (it.status eq ApprovalStatus.IN_DISCUSSION) or
                                (it.status eq ApprovalStatus.IN_REVALUATION))
            }.toList()
                .filter { proposal ->
                    approvedBids.any { it.proposalId == proposal.id } && reviews.none { it.proposalId == proposal.id }
                }.map {
                    ProposalWithAuthors(it, getProposalAuthors(it.id))
                }
        }

        fun get(proposalId: Int) = database.proposals.find { it.id eq proposalId }

        fun isProposalInConflict(proposalId: Int) = database.proposals.any {
            (it.id eq proposalId) and (it.status eq ApprovalStatus.IN_CONFLICT)
        }

        fun updateStatus(proposalId: Int, status: ApprovalStatus) {
            database.proposals.update(Proposal {
                id = proposalId
                this.status = status
            })

            if (status == ApprovalStatus.APPROVED) {
                notifyAuthorsForProposal(proposalId)
            }
        }

        fun getWithReviews(proposalId: Int, authorId: Int? = null) =
            database.proposals.find { it.id eq proposalId }?.let {
                getWithReviewsForAuthor(it, authorId)
            }

        fun setDiscussionStatus(proposalId: Int) {
            ReviewService.remove(proposalId)

            updateStatus(proposalId, ApprovalStatus.IN_DISCUSSION)
        }

        fun setRevaluationStatus(proposalId: Int) {
            ReviewService.invalidate(proposalId)

            updateStatus(proposalId, ApprovalStatus.IN_REVALUATION)
        }

        private fun notifyAuthorsForProposal(proposalId: Int) = database.proposalAuthors
            .filter { it.proposalId eq proposalId }
            .forEach { updateProposalAuthor(proposalId, it.authorId, true) }

        private fun markNotificationAsRead(proposalId: Int, authorId: Int) =
            updateProposalAuthor(proposalId, authorId, false)

        private fun updateProposalAuthor(proposalId: Int, authorId: Int, notification: Boolean) =
            database.proposalAuthors.update(ProposalAuthor {
                this.proposalId = proposalId
                this.authorId = authorId
                this.notification = notification
            })

        fun checkNotifications(userId: Int): Proposal? {
            val proposalAuthor = database.proposalAuthors.firstOrNull { it.authorId eq userId and it.notification }
                ?: return null

            return get(proposalAuthor.proposalId)
        }
    }
}
