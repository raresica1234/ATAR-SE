package server.domain

import org.ktorm.entity.Entity
import java.time.LocalDate

interface Chat : Entity<Chat> {
    companion object : Entity.Factory<Chat>()

    val id: Int
    var userId: Int
    var proposalId: Int
    var message: String
    var timestamp: LocalDate
}