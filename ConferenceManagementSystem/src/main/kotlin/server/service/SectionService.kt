package server.service

import org.ktorm.dsl.*
import org.ktorm.entity.*
import server.database
import server.domain.Proposal
import server.domain.Room
import server.domain.Section
import server.domain.User
import server.sections
import java.time.LocalDate

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

        fun add(section: Section) = section.apply {
            database.sections.add(section)
        }

        fun update(section: Section) = section.apply {
            database.sections.update(section)
        }

        fun isRoomAvailable(roomId: Int, startDate: LocalDate, endDate: LocalDate, ignoreSectionId: Int = 0) =
            database.sections.none {
                // For each associated room check that the dates do not overlap.
                // If does not fain any overlap, returns true -> is available
                (it.id notEq ignoreSectionId) and
                        (it.roomId eq roomId) and
                        ((startDate lessEq it.endDate) or (endDate greaterEq it.startDate))
            }

        fun delete(id: Int) = database.sections.removeIf { it.id eq id }
    }
}