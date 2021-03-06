package client.model

import server.domain.ApprovalStatus
import server.service.ProposalWithAuthors

data class DetailedProposalItemModel(
    val id: Int,
    val name: String,
    val topics: String,
    val keywords: String,
    val authors: String,
    val abstract: String,
    val fullPaper: String,
    val status: ApprovalStatus
) {
    companion object {
        fun from(proposalWithAuthors: ProposalWithAuthors) = with(proposalWithAuthors) {
            DetailedProposalItemModel(
                proposal.id,
                proposal.name,
                proposal.topics.replace("\n", ", "),
                proposal.keywords.replace("\n", ", "),
                authors.joinToString { it.fullName.ifBlank { it.email } },
                proposal.abstractPaper,
                proposal.fullPaper,
                proposal.status
            )
        }
    }

    override fun toString() = name
}