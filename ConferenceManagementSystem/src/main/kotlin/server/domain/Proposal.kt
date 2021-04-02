package server.domain

data class Proposal(
    val id: Int,
    var abstractPaper: String,
    var fullPaper: String,
    var name: String,
    var keywords: String,
    val topics: String
) : BaseEntity<Int>(id)
