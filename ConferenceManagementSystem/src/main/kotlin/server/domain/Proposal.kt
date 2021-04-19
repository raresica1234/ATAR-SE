package server.domain

import org.ktorm.entity.Entity

interface Proposal : Entity<Proposal> {
    companion object : Entity.Factory<Proposal>()

    var id: Int
    var abstractPaper: String
    var fullPaper: String
    var name: String
    var keywords: String
    var topics: String
    var conferenceId: Int
    var sectionId: Int
}