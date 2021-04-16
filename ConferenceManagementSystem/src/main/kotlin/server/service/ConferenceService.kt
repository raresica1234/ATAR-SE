package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.map
import org.ktorm.entity.update
import server.conferences
import server.database
import server.domain.Conference
import server.domain.Proposal
import server.domain.RoleType
import server.domain.Section
import utils.hasPassed
import utils.validateBefore

data class ConferenceWithSectionsAndProposals(
    val conference: Conference,
    val sections: List<Section>,
    val proposals: List<Proposal>
)

class ConferenceService {
    companion object {
        fun getAllActiveWithSectionsAndProposals() = database.conferences.map {
            ConferenceWithSectionsAndProposals(
                it,
                SectionService.getAllByConferenceId(it.id),
                ProposalService.getAllByConferenceId(it.id)
            )
        }.filter { conference ->
            conference.sections.isEmpty() || conference.sections.any { !it.endDate.hasPassed() }
        }

        fun createConference(conference: Conference, chairId: Int) = with(conference) {
            validateDeadlines(this)

            database.conferences.add(this)
            RoleService.add(chairId, id, RoleType.CHAIR)

            this
        }

        fun get(conferenceId: Int) = database.conferences.find { it.id.eq(conferenceId) }

        fun update(conference: Conference) = conference.apply {
            validateDeadlines(this)

            database.conferences.update(this)
        }

        private fun validateDeadlines(conference: Conference) = with(conference) {
            abstractDeadline.validateBefore(paperDeadline, true)
                .validateBefore(biddingDeadline)
                .validateBefore(reviewDeadline)
        }

        fun isFullPaperNeeded(conference: Conference) = conference.abstractDeadline == conference.paperDeadline
    }
}