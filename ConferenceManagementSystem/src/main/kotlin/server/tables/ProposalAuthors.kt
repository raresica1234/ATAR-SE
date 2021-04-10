package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import server.domain.ProposalAuthor
import server.tables.Bids.bindTo

object ProposalAuthors : Table<ProposalAuthor>("proposalauthors") {
    val proposalId = int("proposalid").primaryKey().bindTo { it.proposalId }
    val authorId = int("authorid").primaryKey().bindTo { it.authorId }
}