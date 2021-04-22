package client.view.proposal

import client.controller.proposal.ViewProposalController
import client.view.ViewWithParams
import client.view.component.vBoxPane
import client.view.conference.ConferenceListView
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class ViewProposalView : ViewWithParams(APPLICATION_TITLE) {
    val controller by inject<ViewProposalController>()
    override fun onParamSet() {
        controller.refreshData(getParam<Int>("userId") ?: 0, getParam<Int>("conferenceId") ?: 0)
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
                this += buildLabel("Name: ", controller.model.name)
                this += buildLabel("Topics: ", controller.model.topics)
                this += buildLabel("Keywords: ", controller.model.keywords)
                this += buildLabel("Authors: ", controller.model.authors)
                this += buildLabel("Status: ", controller.model.status)
                this += buildLabel("Recommendation: ", controller.model.recommendation)
                this += buildLabel("Abstract paper: ", controller.model.abstractPaper)
                uploadPaper(controller.model.fullPaperName) {
                    controller.handleFullPaperUpload(it)
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

    private fun buildLabel(labelText: String, binding: SimpleStringProperty) = hbox(8.0) {
        label(labelText)
        text(binding) {
            style { fontWeight = FontWeight.BOLD }
        }
    }
}