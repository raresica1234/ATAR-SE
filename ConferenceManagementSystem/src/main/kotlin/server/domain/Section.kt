package server.domain

import org.ktorm.entity.Entity
import java.time.LocalDate

interface Section : Entity<Section> {
    companion object : Entity.Factory<Section>()

    var id: Int
    var conferenceId: Int
    var name: String
    var sessionChairId: Int
    var startDate: LocalDate
    var endDate: LocalDate
    var roomId: Int
}