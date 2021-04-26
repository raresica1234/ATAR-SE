package client.controller.review

import client.model.ConflictItemModel
import client.model.DetailedProposalItemModel
import server.domain.ApprovalStatus
import server.service.ProposalService
import tornadofx.Controller

class ResolveConflictsController : Controller() {
    val model = ConflictItemModel()

    fun onParamsSet(proposal: DetailedProposalItemModel) {
        model.proposal.set(proposal)
    }

    fun openPaper() = hostServices.showDocument(model.proposal.get().fullPaper)

    fun updateProposalStatus(proposalStatus: ApprovalStatus) {
        ProposalService.updateStatus(model.proposal.get().id, proposalStatus)
    }
}