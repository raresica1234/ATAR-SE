package server.tables

import org.ktorm.schema.*
import server.domain.Chat

object Chats : Table<Chat>("chats") {
    var id = int("id").primaryKey().bindTo { it.id }
    var userId = int("userid").bindTo { it.userId }
    var proposalId = int("proposalid").bindTo { it.proposalId }
    var message = varchar("message").bindTo { it.message }
    var timestamp = datetime("timestamp").bindTo { it.timestamp }
}