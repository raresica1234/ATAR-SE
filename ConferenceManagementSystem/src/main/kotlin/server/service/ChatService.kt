package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.greater
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.map
import server.chats
import server.database
import server.domain.Chat
import java.time.LocalDateTime

class ChatService {
    companion object {
        fun getAllByProposalId(proposalId: Int) = database.chats
            .filter { it.proposalId eq proposalId }
            .map { it.apply { user = UserService.get(userId) } }

        fun getNewByProposalIdAndLastChat(proposalId: Int, lastChat: Chat) = database.chats
            .filter { it.proposalId eq proposalId and (it.id greater lastChat.id) }
            .map { it.apply { user = UserService.get(userId) } }

        fun sendMessage(proposalId: Int, userId: Int, message: String) = Chat {
            this.proposalId = proposalId
            this.userId = userId
            this.message = message
            timestamp = LocalDateTime.now()
        }.apply { database.chats.add(this) }
    }
}