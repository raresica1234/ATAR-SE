package server.domain

import org.ktorm.entity.Entity

interface Review : Entity<Review> {
    companion object : Entity.Factory<Review>()

    val proposal: Proposal
    val pcMember: User
    var reviewType: ReviewType
    var recommendation: String
}