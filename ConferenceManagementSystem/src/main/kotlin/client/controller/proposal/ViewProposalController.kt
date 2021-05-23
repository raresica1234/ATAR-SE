package client.controller.proposal

import client.model.proposal.ViewProposalModel
import client.state.userState
import server.service.ConferenceService
import server.service.ProposalService
import tornadofx.Controller
import java.io.File

class ViewProposalController : Controller() {
    val model = ViewProposalModel()

    fun refreshData(conferenceId: Int) {
        model.conference.set(ConferenceService.get(conferenceId))

        val proposalWithReviews =
            ProposalService.getProposalByConferenceAndAuthorId(conferenceId, userState.user.id) ?: return
        val authors = ProposalService.getProposalAuthors(proposalWithReviews.proposal.id)

        model.setProposal(proposalWithReviews, authors)
    }

    fun handleFullPaperUpload(file: File?) {
        // If a file was selected then update the paper location with the new location
        if (file == null) {
            return
        }

        model.fullPaperLocation.set(file.absolutePath)
        model.fullPaperName.set(file.name)
        ProposalService.updateFullPaper(model.id, file.absolutePath)
    }

    fun handlePresentationUpload(file: File?) {
        if (file == null) {
            return
        }

        model.presentationLocation.set(file.absolutePath)
        model.presentationName.set(file.name)
        ProposalService.updatePresentation(model.id, file.absolutePath)
    }
}