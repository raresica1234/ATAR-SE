package server.domain

import org.ktorm.entity.Entity

interface Bid : Entity<Bid> {
    companion object : Entity.Factory<Bid>()

    var proposalId: Int
    var pcMemberId: Int
    var bidType: BidType
}
