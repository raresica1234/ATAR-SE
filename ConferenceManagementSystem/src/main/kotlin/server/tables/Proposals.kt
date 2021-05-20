package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import server.domain.ApprovalStatus
import server.domain.Proposal

object Proposals : Table<Proposal>("proposals") {
    val id = int("id").primaryKey().bindTo { it.id }
    val abstractPaper = varchar("abstractpaper").bindTo { it.abstractPaper }
    val fullPaper = varchar("fullpaper").bindTo { it.fullPaper }
    val name = varchar("name").bindTo { it.name }
    val keywords = varchar("keywords").bindTo { it.keywords }
    val topics = varchar("topics").bindTo { it.topics }
    val conferenceId = int("conferenceid").bindTo { it.conferenceId }
    val sectionId = int("sectionid").bindTo { it.sectionId }
    val status = enum<ApprovalStatus>("status").bindTo { it.status }
    val presentation = varchar("presentation").bindTo { it.presentation }
}