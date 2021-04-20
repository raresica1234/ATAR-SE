package server.service

import org.ktorm.dsl.eq
import org.ktorm.dsl.inList
import org.ktorm.dsl.notEq
import org.ktorm.dsl.notInList
import org.ktorm.entity.*
import server.database
import server.domain.Room
import server.rooms

class RoomService {
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

        fun getUnusedRoomsWith(roomId: Int = 0): List<Room> {
            val usedRooms = SectionService.getAllRoomsInUse(roomId)

            // This would be nice to work, but it does not :(
//            return database.rooms.filter { it.id.notInList(usedRooms) }.toList()

            return database.rooms
                .sortedBy { it.seatCount }
                .toList()
                .filterNot { room -> usedRooms.contains(room.id) }
        }

        fun get(id: Int) = database.rooms.find { it.id.eq(id) }
    }
}