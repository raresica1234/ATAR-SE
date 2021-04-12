package server.service

import org.ktorm.entity.toList
import server.database
import server.rooms

class RoomSerivce {
    companion object {
        fun getAll() = database.rooms.toList()
    }
}