package server.domain

import java.util.*

data class Section(
    val id: Int,
    val conferenceId: Int,
    var sessionChairId: Int,
    var eventDate: Date,
    var speakers: List<Int>,
    var listeners: List<Int>,
    var roomId: Int
) :
    BaseEntity<Int>(id)
