package server.domain

data class Bid(
    val id: Int,
    val pcMemberId: Int,
    var bidType: BidType
) : BaseEntity<Int>(id)
