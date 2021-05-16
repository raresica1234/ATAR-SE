package client.view.review

import client.controller.review.ReviewPaperController
import client.model.DetailedProposalItemModel
import client.view.ViewWithParams
import client.view.chat.ChatView
import client.view.component.labelWithData
import client.view.component.vBoxPane
import client.view.proposal.ProposalListView
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.text.Font
import server.domain.ApprovalStatus
import server.domain.ReviewType
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class ReviewPaperView : ViewWithParams(APPLICATION_TITLE) {
    private val controller by inject<ReviewPaperController>()

    private val chatButton = button("Chat") {
        action { switchTo(ChatView::class) }
    }

    override fun onParamSet() {
        getParam<DetailedProposalItemModel>("proposal")?.let { controller.onParamsSet(it) }
    }

    override val root = vbox(32.0, alignment = Pos.CENTER) {
        minWidth = 512.0
        minHeight = 400.0
        paddingAll = 32.0

        vbox(16.0) {
            maxWidth = 312.0

            text {
                font = Font(24.0)
                controller.model.proposal.onChange { text = "${it?.name} - Review" }
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
                            controller.model.proposal.onChange {
                                disableProperty().set(it?.fullPaper.isNullOrBlank())
                            }

                            action { controller.openPaper() }
                        }
                    }
                }

                vbox(32.0) {
                    vbox {
                        maxWidth = 192.0

                        label("Review statement")
                        combobox(controller.model.selectedReviewType, ReviewType.values().toList().reversed()) {
                            minWidth = 192.0
                            promptText = "Select a review"
                        }
                    }

                    vbox {
                        maxWidth = 192.0

                        label("Recommendation")
                        textarea(controller.model.recommendation) {
                            minWidth = 192.0
                            isWrapText = true
                            promptText = "Write a recommendation (Optional)"
                        }
                    }
                }
            }
            hbox(16.0, Pos.CENTER_RIGHT) {
                paddingTop = 16.0

                button("Close") {
                    action { switchTo(ProposalListView::class) }
                }

                controller.model.proposal.onChange {
                    if (it?.status == ApprovalStatus.IN_DISCUSSION) {
                        children.add(1, chatButton)
                    } else {
                        children.remove(chatButton)
                    }
                }

                button("Submit") {
                    action {
                        if (controller.submitReview()) {
                            switchTo(ProposalListView::class)
                        }
                    }
                }
            }
        }
    }

    private fun EventTarget.labelWithDataExtractor(
        labelText: String,
        extractor: (DetailedProposalItemModel) -> String
    ) = labelWithData(labelText) {
        controller.model.proposal.onChange {
            text = if (it == null) "-" else extractor(it)
        }
    }
}