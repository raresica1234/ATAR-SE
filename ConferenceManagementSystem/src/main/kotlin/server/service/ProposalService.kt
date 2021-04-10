package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import server.database
import server.proposals

class ProposalService {
    companion object {
        fun getProposalsByConferenceId(conferenceId: Int) = database.proposals
            .filter { it.conferenceId.eq(conferenceId) }
            .toList()
    }
}