package client.view.review

import client.controller.review.ManageReviewsController
import client.view.ViewWithParams
import client.view.component.labelWithData
import client.view.component.vBoxPane
import client.view.proposal.ProposalListView
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.control.cell.CheckBoxListCell
import javafx.scene.text.Font
import server.domain.ReviewType
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.ifNull
import utils.switchTo

class ManageReviewsView : ViewWithParams(APPLICATION_TITLE) {
    private val controller by inject<ManageReviewsController>()

    override fun onParamSet() {
        val isRevaluation = getParam<Boolean>("isRevaluation") ?: false

        getParam<Int>("proposalId")?.let { controller.onParamsSet(it, isRevaluation) }
    }

    override val root = vbox(alignment = Pos.CENTER) {
        paddingAll = 32.0

        vbox(32.0) {
            maxWidth = 735.0

            text("Reviewers & Reviews") {
                font = Font(24.0)

                controller.model.proposal.onChange {
                    text = "${it?.name} - Reviewers & Reviews"
                }
            }

            vbox(8.0) {
                hbox(8.0) {
                    text("Reviewers -") {
                        font = Font(18.0)
                    }
                    text("2/4") {
                        font = Font(18.0)
                        style {
                            textFill = c("red")
                        }
                    }
                }

                hbox(64.0) {
                    listview(controller.model.bids) {
                        minWidth = 512.0
                        maxHeight = 256.0
                        cellFactory = CheckBoxListCell.forListView { it.approved }
                    }

                    vBoxPane(16.0) {
                        minWidth = 256.0

                        text("Reviews") {
                            font = Font(18.0)
                        }

                        vbox(8.0) {
                            labelWithCount(ReviewType.STRONG_ACCEPT)
                            labelWithCount(ReviewType.ACCEPT)
                            labelWithCount(ReviewType.BORDERLINE_PAPER)
                            labelWithCount(ReviewType.REJECT)
                            labelWithCount(ReviewType.STRONG_REJECT)
                            spacer {
                                minHeight = 16.0
                            }
                            labelWithData("Reviews score:") {
                                val reviews = controller.model.reviews

                                reviews.onChange {
                                    text = reviews.sumBy { it.reviewValue }.toString()
                                }
                            }
                            labelWithData("Status:") {
                                controller.model.proposal.onChange {
                                    text = it?.status?.value.ifNull { "-" }
                                }
                            }
                        }
                    }
                }
            }

            hbox(alignment = Pos.CENTER_RIGHT) {
                button("Back") {
                    action {
                        switchTo(ProposalListView::class)
                    }
                }
            }
        }
    }

    private fun EventTarget.labelWithCount(reviewType: ReviewType) =
        labelWithData("${reviewType.displayName}:") {
            val reviews = controller.model.reviews

            reviews.onChange {
                text = reviews.count { it.reviewType == reviewType }.toString()
            }
        }
}