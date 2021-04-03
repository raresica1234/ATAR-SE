package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import server.domain.Review
import server.domain.ReviewType

object Reviews : Table<Review>("reviews") {
    val proposalId = int("proposalid").primaryKey().references(Proposals) { it.proposal }
    val pcMemberId = int("pcMemberid").primaryKey().references(Users) {it.pcMember}
    var reviewType = enum<ReviewType>("reviewtype").bindTo { it.reviewType }
    var recommendation = varchar("recommendation").bindTo { it.recommendation }
}