package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.enum
import org.ktorm.schema.int
import server.domain.Bid
import server.domain.BidType

object Bids : Table<Bid>("bids") {
    var proposalId = int("proposalid").primaryKey().bindTo { it.proposalId }
    var pcMemberId = int("pcmemberid").primaryKey().bindTo { it.pcMemberId }
    var bidType = enum<BidType>("bidtype").bindTo { it.bidType }
    var approved = boolean("approved").bindTo { it.approved }
}