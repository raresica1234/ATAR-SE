package server.service

import org.ktorm.dsl.eq
import org.ktorm.dsl.notEq
import org.ktorm.entity.filter
import org.ktorm.entity.map
import org.ktorm.entity.toList
import server.database
import server.domain.Proposal
import server.domain.Room
import server.domain.Section
import server.domain.User
import server.sections

data class SectionWithProposals(
    val section: Section,
    val proposals: List<Proposal>,
    val room: Room?,
    val sessionChair: User?
)

class SectionService {
    companion object {
        fun getAllByConferenceId(conferenceId: Int) = database.sections
            .filter { it.conferenceId.eq(conferenceId) }
            .toList()

        fun getAllRoomsInUse(exceptRoomId: Int = 0) = database.sections
            .filter { it.roomId.notEq(exceptRoomId) }
            .map { it.roomId }
            .distinct()

        fun getAllWithProposalsByConference(conferenceId: Int) = database.sections
            .filter { it.conferenceId.eq(conferenceId) }
            .map {
                SectionWithProposals(
                    it,
                    ProposalService.getAllBySectionId(it.id),
                    RoomService.get(it.roomId),
                    UserService.get(it.sessionChairId)
                )
            }
    }
}