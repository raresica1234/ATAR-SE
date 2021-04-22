package client.controller.proposal

import client.model.proposal.SubmitProposalModel
import client.state.userState
import server.service.ProposalService
import server.service.UserService
import tornadofx.Controller
import utils.ValidationException
import utils.isNullOrBlank
import java.io.File

class SubmitProposalController : Controller() {
    val model = SubmitProposalModel()

    fun handleSubmitProposalClick(): Boolean {
        return try {
            validateFields()

            val authors = mutableListOf(userState.user.email)

            model.authors.get()?.let {
                if (it.isNotBlank()) authors.addAll(it.split("\n"))
            }

            UserService.createMissingAccounts(authors)
            ProposalService.submitProposal(model.toProposal(), authors)
            true
        } catch (exception: ValidationException) {
            exception.displayError()
            false
        }
    }

    fun handleFullPaperUpload(file: File?) {
        // If a file was selected then update the paper location with the new location
        if (file == null) {
            return
        }

        model.fullPaperLocation.set(file.absolutePath)
        model.fullPaperName.set(file.name)
    }

    private fun validateFields() {
        if (model.name.isNullOrBlank()) {
            throw ValidationException(
                "Name of the proposal is empty!",
                "The proposal name must be set before continuing, try again."
            )
        }

        if (model.topics.isNullOrBlank()) {
            throw ValidationException(
                "The topics of the proposal are empty!",
                "The topics must be set before continuing, try again."
            )
        }

        if (model.keywords.isNullOrBlank()) {
            throw ValidationException(
                "The keywords of the proposal are empty!",
                "The keywords must be set before continuing, try again."
            )
        }

        if (model.abstractPaper.isNullOrBlank()) {
            throw ValidationException(
                "The abstract paper of the proposal is empty!",
                "The abstract paper must be set before continuing, try again."
            )
        }

        if (model.conference.get().isFullPaper && model.fullPaperLocation.isNullOrBlank()) {
            throw ValidationException(
                "The full paper of the proposal is empty!",
                "The full paper must be set before continuing, try again."
            )
        }
    }
}