package client.controller.chat

import client.model.chat.ChatModel
import client.state.userState
import server.service.ChatService
import tornadofx.Controller
import utils.clear

class ChatController : Controller() {
    val model = ChatModel()

    fun onParamsSet(proposalId: Int) {
        model.message.clear()
        model.proposalId.set(proposalId)
        fetchMessages(proposalId)
    }

    fun sendMessage() {
        model.message.get()?.let {
            ChatService.sendMessage(model.proposalId.get(), userState.user.id, it)
            model.message.clear()
        }

        fetchNewMessages()
    }

    private fun fetchMessages(proposalId: Int) {
        runAsync {
            ChatService.getAllByProposalId(proposalId)
        } ui {
            model.messages.setAll(it)
        }
    }

    private fun fetchNewMessages() {
        val proposalId = model.proposalId.get()
        val lastChat = model.messages.lastOrNull() ?: return fetchMessages(proposalId)

        runAsync {
            ChatService.getNewByProposalIdAndLastChat(proposalId, lastChat)
        } ui {
            model.messages.addAll(it)
        }
    }
}