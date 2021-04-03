package server.domain

import org.ktorm.entity.Entity

interface Participant : Entity<Participant> {
    companion object : Entity.Factory<Participant>()

    val user: User
    val section: Section
    var isSpeaker: Boolean
}