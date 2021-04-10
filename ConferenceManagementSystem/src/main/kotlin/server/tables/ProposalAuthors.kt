package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import server.domain.ProposalAuthor

object ProposalAuthors : Table<ProposalAuthor>("proposalauthors") {
    val proposalId = int("proposalid").primaryKey().references(Proposals) { it.proposal }
    val authorId = int("authorid").primaryKey().references(Users) { it.author }
}