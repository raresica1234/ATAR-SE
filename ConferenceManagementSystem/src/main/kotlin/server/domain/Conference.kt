package server.domain

import java.util.*

data class Conference(
    val id: Int,
    var name: String,
    var abstractDeadline: Date,
    var paperDeadline: Date,
    var biddingDeadline: Date,
    var reviewDeadline: Date,
    var reviewerCount: Int = 2
) :
    BaseEntity<Int>(id)
