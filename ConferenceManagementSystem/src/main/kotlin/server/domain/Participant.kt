package server.domain

data class Participant(
    val userId: Int,
    val sectionId: Int,
    var isSpeaker: Boolean
) : BaseEntity<Pair<Int, Int>>(Pair(userId, sectionId))
