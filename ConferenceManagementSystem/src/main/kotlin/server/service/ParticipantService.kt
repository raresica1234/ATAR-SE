package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.count
import org.ktorm.entity.removeIf
import server.database
import server.domain.Participant
import server.participants

class ParticipantService {
    companion object {
        fun addParticipant(sectionId: Int, userId: Int) = addUserToSection(sectionId, userId, false)

        fun addSpeaker(sectionId: Int, userId: Int) = addUserToSection(sectionId, userId, true)

        private fun addUserToSection(sectionId: Int, userId: Int, isSpeaker: Boolean) =
            database.participants.add(Participant {
                this.sectionId = sectionId
                this.userId = userId
                this.isSpeaker = isSpeaker
            })

        fun delete(sectionId: Int, userId: Int) = database.participants.removeIf {
            it.sectionId eq sectionId and (it.userId eq userId)
        }

        fun getSectionParticipants(sectionId: Int) = database.participants.count { it.sectionId eq sectionId }
    }
}