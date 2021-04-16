package client.view.conference

import client.controller.conference.SubmitProposalController
import client.view.ViewWithParams
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.stage.FileChooser
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class SubmitProposalView : ViewWithParams(APPLICATION_TITLE) {
    companion object {
        const val PARAM_CONFERENCE = "conference"
    }

    val PAPER_FILTERS =
        arrayOf(FileChooser.ExtensionFilter("Full paper (*.pdf, *.docx, *.txt)", "*.pdf", "*.docx", "*.txt"))

    private val TEXT_AREA_WIDTH = 128.0
    private val TEXT_AREA_HEIGHT = 256.0
    private val VIEW_WIDTH = 416.0

    private val controller by inject<SubmitProposalController>()

    override fun onParamSet() {
        controller.model.conference.set(getParam(PARAM_CONFERENCE))
    }

    override val root = vbox(32.0, Pos.CENTER) {
        paddingAll = 32.0

        text {
            font = Font(24.0)
            controller.model.conference.onChange { text = "Proposal for " + it?.name }
        }

        vbox(32.0, Pos.CENTER) {
            vbox {
                maxWidth = VIEW_WIDTH
                label("Name")
                textfield(controller.model.name) {
                    maxWidth = VIEW_WIDTH
                    promptText = "Name"
                }
            }
            hbox(16.0) {
                maxWidth = VIEW_WIDTH
                vbox {
                    label("Topics")
                    textarea(controller.model.topics) {
                        maxWidth = TEXT_AREA_WIDTH
                        maxHeight = TEXT_AREA_HEIGHT

                        promptText = "Topics separated by new lines"
                    }
                }

                vbox {
                    label("Keywords")
                    textarea(controller.model.keywords) {
                        maxWidth = TEXT_AREA_WIDTH
                        maxHeight = TEXT_AREA_HEIGHT

                        promptText = "Keywords separated by new lines"
                    }
                }

                vbox {
                    label("Other authors")
                    textarea(controller.model.authors) {
                        maxWidth = TEXT_AREA_WIDTH
                        maxHeight = TEXT_AREA_HEIGHT

                        promptText = "Author emails separated by new lines"
                    }
                }
            }

            vbox {
                maxWidth = VIEW_WIDTH
                label("Abstract paper")
                textarea(controller.model.abstractPaper) {
                    maxWidth = VIEW_WIDTH
                    maxHeight = TEXT_AREA_HEIGHT * 2
                    promptText = "Abstract paper"
                }
            }

            hbox(32.0) {
                maxWidth = VIEW_WIDTH
                hbox(8.0, Pos.CENTER) {
                    label("Full paper:") {
                        minWidth = 70.0
                    }

                    label(controller.model.fullPaperName) {
                        minWidth = 180.0
                        maxWidth = 180.0
                    }
                }
                button("Upload") {
                    minWidth = 128.0
                    action {
                        val fullPapers =
                            chooseFile(title = "Select full paper location", filters = PAPER_FILTERS)

                        // If a file was selected then update the paper location with the new location
                        if (fullPapers.isNotEmpty()) {
                            controller.model.fullPaperLocation.set(fullPapers[0].absolutePath)
                            controller.model.fullPaperName.set(fullPapers[0].name)
                        }
                    }
                }
            }

            hbox(180.0) {
                alignment = Pos.CENTER
                maxWidth = VIEW_WIDTH

                button("Back") {
                    minWidth = 80.0
                    action {
                        switchTo(ConferenceListView::class)
                    }
                }

                button("Submit") {
                    minWidth = 80.0
                    action {
                        if (controller.handleSubmitProposalClick()) {
                            switchTo(ConferenceListView::class)
                        }
                    }
                }
            }
        }
    }
}