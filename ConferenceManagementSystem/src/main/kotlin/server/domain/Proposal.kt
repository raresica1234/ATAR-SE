package server.domain

data class Proposal(
    val id: Int,
    var abstract: String,
    var fullPaPer: String,
    var name: String,
    var keywords: List<String>,
    val topics: List<String>
) :
    BaseEntity<Int>(id)
