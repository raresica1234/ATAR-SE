package server.service

import org.ktorm.entity.map
import server.conferences
import server.database
import server.domain.Conference
import server.domain.Proposal
import server.domain.Section
import utils.hasPassed

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
                SectionService.getSectionsByConferenceId(it.id),
                ProposalService.getProposalsByConferenceId(it.id)
            )
        }.filter { conference ->
            conference.sections.any { !it.endDate.hasPassed() }
        }
    }
}