package server.domain

data class Proposal(
    val id: Int,
    var abstractPaper: String,
    var fullPaper: String,
    var name: String,
    var keywords: String,
    var topics: String
) : BaseEntity<Int>(id)
