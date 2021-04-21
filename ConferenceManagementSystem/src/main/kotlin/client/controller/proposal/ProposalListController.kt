package client.controller.proposal

import client.model.proposal.ProposalListModel
import tornadofx.Controller

class ProposalListController : Controller() {
    val model = ProposalListModel()

    fun onParamsSet(conferenceId: Int) {

    }
}