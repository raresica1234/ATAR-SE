package server.domain

import org.ktorm.entity.Entity
import java.time.LocalDate

interface Conference : Entity<Conference> {
    companion object : Entity.Factory<Conference>()

    var id: Int
    var name: String
    var abstractDeadline: LocalDate?
    var paperDeadline: LocalDate?
    var biddingDeadline: LocalDate?
    var reviewDeadline: LocalDate?
    var reviewerCount: Int
}