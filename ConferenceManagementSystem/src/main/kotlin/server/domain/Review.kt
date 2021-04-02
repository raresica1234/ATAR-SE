package server.domain

data class Review(
    val proposalId: Int,
    val pcMember: Int,
    var reviewType: ReviewType,
    var recommandation: String
) : BaseEntity<Pair<Int, Int>>(Pair(proposalId, pcMember))
