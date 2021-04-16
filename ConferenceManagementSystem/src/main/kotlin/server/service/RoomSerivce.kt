package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.removeIf
import org.ktorm.entity.toList
import org.ktorm.entity.update
import server.database
import server.domain.Room
import server.rooms

class RoomSerivce {
    companion object {
        fun getAll() = database.rooms.toList()

        fun add(seatCount: Int): Room {
            val room = Room {
                this.seatCount = seatCount
            }

            database.rooms.add(room)

            return room
        }

        fun delete(id: Int) = database.rooms.removeIf { it.id eq id }

        fun update(room: Room) = database.rooms.update(room)
    }

}