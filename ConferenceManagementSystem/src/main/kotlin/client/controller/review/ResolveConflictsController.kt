package client.controller.review

import client.model.DetailedProposalItemModel
import javafx.beans.property.SimpleObjectProperty
import server.domain.ApprovalStatus
import server.service.ProposalService
import tornadofx.Controller

class ResolveConflictsController : Controller() {
    val proposal: SimpleObjectProperty<DetailedProposalItemModel> = SimpleObjectProperty()

    fun onParamsSet(proposal: DetailedProposalItemModel) {
        this.proposal.set(proposal)
    }

    fun openPaper() = hostServices.showDocument(proposal.get().fullPaper)

    fun updateProposalStatus(proposalStatus: ApprovalStatus) {
        ProposalService.updateStatus(proposal.get().id, proposalStatus)
    }
}