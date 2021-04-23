package client.view.review

import client.controller.review.ManageReviewsController
import client.view.ViewWithParams
import client.view.component.labelWithData
import client.view.component.vBoxPane
import client.view.proposal.ProposalListView
import javafx.geometry.Pos
import javafx.scene.control.cell.CheckBoxListCell
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class ManageReviewsView : ViewWithParams(APPLICATION_TITLE) {
    private val controller by inject<ManageReviewsController>()

    override fun onParamSet() {
        getParam<Int>("proposalId")?.let { controller.onParamsSet(it) }
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
                text("Reviewers") {
                    font = Font(18.0)
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
                            labelWithData("Strong accept reviews:")
                            labelWithData("Accept reviews:")
                            labelWithData("Borderline reviews:")
                            labelWithData("Reject reviews:")
                            labelWithData("Strong reject reviews:")
                            labelWithData("Status:")
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
}