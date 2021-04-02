package server.domain

data class Role(
    val userId: Int,
    val conferenceId: Int,
    var roleType: RoleType
) :
    BaseEntity<Pair<Int, Int>>(Pair(userId, conferenceId))
