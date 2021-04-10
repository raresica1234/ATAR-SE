package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import server.domain.Proposal

object Proposals : Table<Proposal>("proposals") {
    val id = int("id").primaryKey().bindTo { it.id }
    var abstractPaper = varchar("abstractpaper").bindTo { it.abstractPaper }
    var fullPaper = varchar("fullpaper").bindTo { it.fullPaper }
    var name = varchar("name").bindTo { it.name }
    var keywords = varchar("keywords").bindTo { it.keywords }
    var topics = varchar("topics").bindTo { it.topics }
    var conferenceId = int("conferenceid").references(Conferences) { it.conference }
    var sectionId = int("sectionid").references(Sections) { it.section }
}