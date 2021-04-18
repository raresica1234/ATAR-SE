package client.controller.proposal

import client.model.proposal.ViewProposalModel
import server.service.ConferenceService
import tornadofx.Controller

class ViewProposalController : Controller() {
    val model = ViewProposalModel()

    fun refreshData(userId: Int, conferenceId: Int) {
        model.conference.set(ConferenceService.get(conferenceId))
    }

}