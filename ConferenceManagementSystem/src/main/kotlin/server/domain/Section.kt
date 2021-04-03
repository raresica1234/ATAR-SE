package server.domain

import java.util.*

data class Section(
    val id: Int,
    val conferenceId: Int,
    var name: String,
    var sessionChairId: Int,
    var startDate: Date,
    var endDate: Date,
    var roomId: Int
) : BaseEntity<Int>(id)
