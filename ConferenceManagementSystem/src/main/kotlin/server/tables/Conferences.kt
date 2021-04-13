package server.tables

import org.ktorm.schema.*
import server.domain.Conference

object Conferences : Table<Conference>("conferences") {
    var id = int("id").primaryKey().bindTo { it.id }
    var name = varchar("name").bindTo { it.name }
    var abstractDeadline = date("abstractdeadline").bindTo { it.abstractDeadline }
    var paperDeadline = date("paperdeadline").bindTo { it.paperDeadline }
    var biddingDeadline = date("biddingdeadline").bindTo { it.biddingDeadline }
    var reviewDeadline = date("reviewdeadline").bindTo { it.reviewDeadline }
    var reviewerCount = int("reviewercount").bindTo { it.reviewerCount }
}