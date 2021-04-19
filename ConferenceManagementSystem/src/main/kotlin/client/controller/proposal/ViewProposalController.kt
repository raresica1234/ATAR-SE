package client.controller.proposal

import client.model.proposal.ViewProposalModel
import server.domain.Bid
import server.domain.Review
import server.service.ConferenceService
import server.service.ProposalService
import tornadofx.Controller
import java.io.File

class ViewProposalController : Controller() {
    val model = ViewProposalModel()

    fun refreshData(authorId: Int, conferenceId: Int) {
        model.conference.set(ConferenceService.get(conferenceId))
        val proposal = ProposalService.getProposalByConferenceAndAuthorId(conferenceId, authorId) ?: return
        val authors = ProposalService.getProposalAuthors(proposal.proposal.id)

        with(model) {
            id = proposal.proposal.id
            name.set(proposal.proposal.name)
            topics.set(proposal.proposal.topics.replace("\n", ", "))
            keywords.set(proposal.proposal.keywords.replace("\n", ", "))
            this.authors.set(authors.joinToString { it.fullName })
            abstractPaper.set(proposal.proposal.abstractPaper)
            fullPaperLocation.set(proposal.proposal.fullPaper)
            status.set(getStatus(proposal.bids, proposal.reviews))
            recommendation.set(proposal.reviews.joinToString("\n\n") { it.recommendation }.ifBlank { "-" })
        }
        println(proposal.proposal)
    }

    private fun getStatus(bids: List<Bid>, reviews: List<Review>): String {
        if (bids.isEmpty()) return "To be reviewed"
        if (bids.size != reviews.size) return "In review"
        return if (reviews.sumBy { it.reviewType.value } > 0) "Approved" else "Rejected"
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

}