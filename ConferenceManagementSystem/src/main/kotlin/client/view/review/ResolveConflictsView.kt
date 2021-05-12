package client.view.review

import client.controller.review.ResolveConflictsController
import client.model.DetailedProposalItemModel
import client.view.ViewWithParams
import client.view.component.labelWithData
import client.view.component.vBoxPane
import client.view.proposal.ProposalListView
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.text.Font
import server.domain.ApprovalStatus
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class ResolveConflictsView : ViewWithParams(APPLICATION_TITLE) {
    private val controller by inject<ResolveConflictsController>()

    override fun onParamSet() {
        getParam<DetailedProposalItemModel>("proposal")?.let { controller.onParamsSet(it) }
    }

    override val root = vbox(alignment = Pos.CENTER) {
        paddingAll = 32.0

        vbox(16.0) {
            maxWidth = 312.0

            text {
                font = Font(24.0)
                controller.proposal.onChange { text = "${it?.name} - Resolve conflicts" }
            }
            hbox(32.0) {
                vBoxPane(8.0) {
                    minWidth = 400.0

                    labelWithDataExtractor("Name:") { it.name }
                    labelWithDataExtractor("Topics:") { it.topics }
                    labelWithDataExtractor("Keywords:") { it.keywords }
                    labelWithDataExtractor("Authors:") { it.authors }
                    labelWithDataExtractor("Status:") { it.status.value }
                    labelWithDataExtractor("Abstract paper:") { it.abstract }

                    hbox(16.0, Pos.CENTER_LEFT) {
                        paddingTop = 16.0

                        label("Full paper:")
                        button("Open") {
                            controller.proposal.onChange {
                                disableProperty().set(it?.fullPaper.isNullOrBlank())
                            }

                            action { controller.openPaper() }
                        }
                    }
                }
            }

            hbox(16.0, Pos.CENTER_RIGHT) {
                paddingTop = 16.0

                button("Close") {
                    action { switchTo(ProposalListView::class) }
                }

                button("Request discussion") {
                    action {
                        controller.requestDiscussion()
                        switchTo(ProposalListView::class)
                    }
                }

                button("Request revaluation") {
                    action {
                        switchTo(
                            ManageReviewsView::class,
                            "proposalId" to controller.proposal.get().id,
                            "isRevaluation" to true
                        )
                    }
                }

                button("Reject") {
                    action {
                        controller.updateProposalStatus(ApprovalStatus.REJECTED)
                        switchTo(ProposalListView::class)
                    }
                }

                button("Approve") {
                    action {
                        controller.updateProposalStatus(ApprovalStatus.APPROVED)
                        switchTo(ProposalListView::class)
                    }
                }
            }
        }
    }

    private fun EventTarget.labelWithDataExtractor(
        labelText: String,
        extractor: (DetailedProposalItemModel) -> String
    ) = labelWithData(labelText) {
        controller.proposal.onChange {
            text = if (it == null) "-" else extractor(it)
        }
    }
}