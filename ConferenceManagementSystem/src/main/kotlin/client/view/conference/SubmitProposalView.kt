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

    private val TEXT_AREA_WIDTH = 384.0
    private val TEXT_AREA_HEIGHT = 64.0

    private val controller by inject<SubmitProposalController>()

    override fun onParamSet() {
        controller.model.conference.set(getParam(PARAM_CONFERENCE))
    }

    override val root = vbox(32.0, Pos.CENTER) {
        minWidth = 448.0
        minHeight = 768.0

        text {
            font = Font(24.0)
            controller.model.conference.onChange { text = "Proposal for " + it?.name }
        }

        hbox(32.0, Pos.CENTER) {
            vbox(32.0) {
                vbox {
                    label("Name")
                    textfield(controller.model.name) {
                        maxWidth = TEXT_AREA_WIDTH

                        promptText = "Name"
                    }
                }

                vbox {
                    label("Topics")
                    textarea(controller.model.topics) {
                        maxWidth = TEXT_AREA_WIDTH
                        maxHeight = TEXT_AREA_HEIGHT

                        promptText = "example1, example2"
                    }
                }

                vbox {
                    label("Keywords")
                    textarea(controller.model.keywords) {
                        maxWidth = TEXT_AREA_WIDTH
                        maxHeight = TEXT_AREA_HEIGHT

                        promptText = "example1, example2"
                    }
                }

                vbox {
                    label("Other authors")
                    textarea(controller.model.authors) {
                        maxWidth = TEXT_AREA_WIDTH
                        maxHeight = TEXT_AREA_HEIGHT

                        promptText = "example1@mail.com, example2@mail.com"
                    }
                }

                vbox {
                    label("Abstract paper")
                    textarea(controller.model.abstractPaper) {
                        maxWidth = TEXT_AREA_WIDTH
                        maxHeight = TEXT_AREA_HEIGHT * 2

                        promptText = "Abstract paper"
                    }
                }

                hbox {
                    alignment = Pos.CENTER
                    vbox {
                        alignment = Pos.CENTER
                        label("Full paper:")
                    }
                    vbox {
                        alignment = Pos.CENTER
                        label(controller.model.fullPaperName)
                    }
                    button("Upload") {
                        minWidth = 128.0
                        action {
                            val fullPapers = chooseFile(title = "Select full paper location", filters = PAPER_FILTERS)

                            // If a file was selected then update the paper location with the new location
                            if (fullPapers.isNotEmpty()) {
                                controller.model.fullPaperLocation.set(fullPapers[0].absolutePath)
                                controller.model.fullPaperName.set(fullPapers[0].name)
                            }
                        }
                    }
                }

                hbox {
                    alignment = Pos.CENTER
                    button("Submit") {
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
}