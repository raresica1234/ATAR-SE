package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.removeIf
import org.ktorm.entity.toList
import server.database
import server.domain.Room
import server.rooms

class RoomSerivce {
    companion object {
        fun getAll() = database.rooms.toList()
        fun add(newRoom: Room) = database.rooms.add(newRoom)
        fun remove(id: Int) = database.rooms.removeIf { it.id eq id }
    }

}