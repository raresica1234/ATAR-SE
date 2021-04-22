package client.model.proposal

import server.service.ProposalWithAuthors

data class ProposalListItemModel(
    val id: Int,
    val name: String,
    val topics: String,
    val keywords: String,
    val authors: String,
    val abstract: String,
    val status: String
) {
    companion object {
        fun from(proposalWithAuthors: ProposalWithAuthors) = with(proposalWithAuthors) {
            ProposalListItemModel(
                proposal.id,
                proposal.name,
                proposal.topics.replace("\n", ", "),
                proposal.keywords.replace("\n", ", "),
                authors.joinToString { it.fullName.ifBlank { it.email } },
                proposal.abstractPaper,
                proposal.status.value
            )
        }
    }

    override fun toString() = name
}