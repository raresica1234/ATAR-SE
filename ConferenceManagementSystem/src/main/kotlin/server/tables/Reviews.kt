package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import server.domain.Review
import server.domain.ReviewType
import server.tables.Bids.bindTo

object Reviews : Table<Review>("reviews") {
    val proposalId = int("proposalid").primaryKey().bindTo { it.proposalId }
    val pcMemberId = int("pcmemberid").primaryKey().bindTo { it.pcMemberId }
    var reviewType = enum<ReviewType>("reviewtype").bindTo { it.reviewType }
    var recommendation = varchar("recommendation").bindTo { it.recommendation }
}