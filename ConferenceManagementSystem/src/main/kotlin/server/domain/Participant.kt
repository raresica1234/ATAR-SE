package server.domain

import org.ktorm.entity.Entity

interface Participant : Entity<Participant> {
    companion object : Entity.Factory<Participant>()

    var userId: Int
    var sectionId: Int
    var isSpeaker: Boolean
}