package server.domain

import org.ktorm.entity.Entity

interface Participant : Entity<Participant> {
    companion object : Entity.Factory<Participant>()

    var user: User
    var section: Section
    var isSpeaker: Boolean
}