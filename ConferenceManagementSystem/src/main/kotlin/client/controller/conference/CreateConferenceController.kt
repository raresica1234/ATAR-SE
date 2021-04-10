package client.controller.conference

import client.model.UserItemModel
import client.model.conference.CreateConferenceModel
import client.state.userState
import server.service.UserService
import tornadofx.Controller

class CreateConferenceController : Controller() {
    val model = CreateConferenceModel()

    init {
        refreshChairs()
    }

    private fun refreshChairs() {
        val chairs = UserService.getAll(userState.user.id)
            .map { UserItemModel(it.id, it.fullName, it.email) }

        model.chairs.setAll(chairs)
    }

}