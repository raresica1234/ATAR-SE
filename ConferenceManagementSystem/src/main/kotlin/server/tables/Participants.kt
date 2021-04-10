package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import server.domain.Participant
import server.tables.Bids.bindTo

object Participants : Table<Participant>("participants") {
    val userId = int("userid").primaryKey().bindTo { it.userId }
    val sectionId = int("sectionid").primaryKey().bindTo { it.sectionId }
    var isSpeaker = boolean("isspeaker").bindTo { it.isSpeaker }
}