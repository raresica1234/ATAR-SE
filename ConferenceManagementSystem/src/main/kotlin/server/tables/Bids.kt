package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import server.domain.Bid
import server.domain.BidType

object Bids : Table<Bid>("bids") {
    val id = int("id").primaryKey().bindTo { it.id }
    val pcMemberId = int("pcmemberid").references(Users) { it.pcMember }
    var bidType = enum<BidType>("bidtype").bindTo { it.bidType }
}