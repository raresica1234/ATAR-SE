package client.view.proposal

import client.controller.proposal.ViewProposalController
import client.view.ViewWithParams
import client.view.component.labelWithData
import client.view.component.vBoxPane
import client.view.conference.ConferenceListView
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class ViewProposalView : ViewWithParams(APPLICATION_TITLE) {
    val controller by inject<ViewProposalController>()
    override fun onParamSet() {
        controller.refreshData(getParam<Int>("conferenceId") ?: 0)
    }

    override val root = vbox(32.0, alignment = Pos.CENTER) {
        minWidth = 512.0
        minHeight = 448.0

        vbox(16.0) {
            maxWidth = 312.0

            text {
                alignment = Pos.CENTER

                font = Font(24.0)
                controller.model.conference.onChange { text = "${it?.name} - Your proposal" }
            }
            vBoxPane {
                scrollpane(fitToWidth = true) {
                    maxHeight = 256.0

                    vbox {
                        labelWithData("Name: ", controller.model.name)
                        labelWithData("Topics: ", controller.model.topics)
                        labelWithData("Keywords: ", controller.model.keywords)
                        labelWithData("Authors: ", controller.model.authors)
                        labelWithData("Status: ", controller.model.status)
                        labelWithData("Recommendation: ", controller.model.recommendation)
                        labelWithData("Abstract paper: ", controller.model.abstractPaper)
                        uploadPaper(controller.model.fullPaperName) {
                            controller.handleFullPaperUpload(it)
                        }
                    }
                }
            }
            vbox {
                alignment = Pos.CENTER_RIGHT
                paddingTop = 16.0
                button("Close") {
                    minWidth = 128.0
                    action { switchTo(ConferenceListView::class) }
                }
            }
        }
    }
}