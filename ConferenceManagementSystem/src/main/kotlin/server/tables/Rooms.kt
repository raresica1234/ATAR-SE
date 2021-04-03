package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import server.domain.Room

object Rooms : Table<Room>("rooms") {
    val id = int("id").primaryKey().bindTo { it.id }
    var seatCount = int("seatcount").bindTo { it.seatCount }
}