package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import server.database
import server.sections

class SectionService {
    companion object {
        fun getSectionsByConferenceId(conferenceId: Int) = database.sections
            .filter { it.conferenceId.eq(conferenceId) }
            .toList()
    }
}