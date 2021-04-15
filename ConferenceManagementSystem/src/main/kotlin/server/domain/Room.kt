package server.domain

import org.ktorm.entity.Entity

interface Room : Entity<Room> {
    companion object : Entity.Factory<Room>()

    var id: Int
    var seatCount: Int
}

