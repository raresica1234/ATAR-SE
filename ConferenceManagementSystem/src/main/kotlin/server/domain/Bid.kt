package server.domain

import org.ktorm.entity.Entity

interface Bid : Entity<Bid> {
    companion object : Entity.Factory<Bid>()

    val id: Int
    val pcMember: User
    var bidType: BidType
}
