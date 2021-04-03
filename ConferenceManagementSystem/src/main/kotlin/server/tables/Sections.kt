package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import server.domain.Section

object Sections : Table<Section>("sections") {
    val id = int("id").primaryKey().bindTo { it.id }
    val conferenceId = int("conferenceid").references(Conferences) { it.conference }
    var name = varchar("name").bindTo { it.name }
    var sessionChairId = int("sessionchairid").bindTo { it.sessionChairId }
    var startDate = date("startdate").bindTo { it.startDate }
    var endDate = date("enddate").bindTo { it.endDate }
    var roomId = int("roomid").references(Rooms) { it.room }
}