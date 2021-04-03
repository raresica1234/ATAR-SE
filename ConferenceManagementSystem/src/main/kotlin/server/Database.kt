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

val Database.bids get() = this.sequenceOf(Bids)
val Database.conferences get() = this.sequenceOf(Conferences)
val Database.participants get() = this.sequenceOf(Participants)
val Database.proposals get() = this.sequenceOf(Proposals)
val Database.reviews get() = this.sequenceOf(Reviews)
val Database.roles get() = this.sequenceOf(Roles)
val Database.rooms get() = this.sequenceOf(Rooms)
val Database.sections get() = this.sequenceOf(Sections)
val Database.users get() = this.sequenceOf(Users)
