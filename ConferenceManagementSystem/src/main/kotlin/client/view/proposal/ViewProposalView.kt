package client.view.proposal

import client.controller.proposal.ViewProposalController
import client.view.ViewWithParams
import client.view.component.vBoxPane
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE

class ViewProposalView : ViewWithParams(APPLICATION_TITLE) {
    val controller by inject<ViewProposalController>()
    override fun onParamSet() {
        controller.refreshData(getParam<Int>("userId")?:0, getParam<Int>("conferenceId")?:0)
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
            vBoxPane(8.0) {
                this += buildLabel("Name: ")
                this += buildLabel("Topics: ")
                this += buildLabel("Keywords: ")
                this += buildLabel("Authors: ")
                this += buildLabel("Status: ")
                this += buildLabel("Recommendation: ")

                hbox(64.0, alignment = Pos.CENTER) {
                    vbox {
                        alignment = Pos.CENTER_LEFT

                        label("Abstract paper")
                        button("Upload") {
                            minWidth = 128.0
                        }
                    }
                    vbox {
                        alignment = Pos.CENTER_LEFT

                        label("Full paper")
                        button("Upload") {
                            minWidth = 128.0
                        }
                    }
                }
            }
            vbox {
                alignment = Pos.CENTER_RIGHT
                paddingTop = 16.0
                button("Close") {
                    minWidth = 128.0
                }
            }
        }
    }

    private fun buildLabel(labelText: String) = hbox(8.0) {
        label(labelText)
    }
}