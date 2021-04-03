package server.domain

data class Room(
    val id: Int,
    var seatCount: Int
) : BaseEntity<Int>(id)
