package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import server.domain.Participant

object Participants : Table<Participant>("participants") {
    val userId = int("userid").primaryKey().references(Users) { it.user }
    val sectionId = int("sectionid").primaryKey().references(Sections) { it.section }
    var isSpeaker = boolean("isspeaker").bindTo { it.isSpeaker }
}