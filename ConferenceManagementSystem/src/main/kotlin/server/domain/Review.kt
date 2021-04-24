package server.domain

import org.ktorm.entity.Entity

interface Review : Entity<Review> {
    companion object : Entity.Factory<Review>()

    var proposalId: Int
    var pcMemberId: Int
    var reviewType: ReviewType
    var recommendation: String

    val reviewValue get() = reviewType.value
}