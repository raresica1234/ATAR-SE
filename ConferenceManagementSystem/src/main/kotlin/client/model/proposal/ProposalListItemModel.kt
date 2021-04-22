package client.model.proposal

data class ProposalListItemModel(
    val id: Int,
    val name: String,
    val topics: String,
    val keywords: String,
    val authors: String,
    val abstract: String,
    val status: String
)