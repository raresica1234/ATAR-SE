package server

import org.ktorm.database.Database
import org.ktorm.entity.sequenceOf
import org.postgresql.Driver
import server.tables.*
import utils.SQL_PASSWORD
import utils.SQL_URL
import utils.SQL_USERNAME

val database = Database.connect(
    url = SQL_URL,
    user = SQL_USERNAME,
    password = SQL_PASSWORD,
    driver = Driver::class.qualifiedName
)

val Database.bids get() = sequenceOf(Bids)
val Database.chats get() = sequenceOf(Chats)
val Database.conferences get() = sequenceOf(Conferences)
val Database.participants get() = sequenceOf(Participants)
val Database.proposalAuthors get() = sequenceOf(ProposalAuthors)
val Database.proposals get() = sequenceOf(Proposals)
val Database.reviews get() = sequenceOf(Reviews)
val Database.roles get() = sequenceOf(Roles)
val Database.rooms get() = sequenceOf(Rooms)
val Database.sections get() = sequenceOf(Sections)
val Database.users get() = sequenceOf(Users)
