package client.view.conference

import client.controller.conference.ModifyConferenceController
import client.model.ProposalItemModel
import client.model.conference.ModifyConferenceSectionModel
import client.view.ViewWithParams
import client.view.component.datePicker
import client.view.component.setNode
import client.view.component.vBoxPane
import client.view.proposal.selectProposalDialog
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.cell.CheckBoxListCell
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.onBlur
import utils.switchTo
import javax.swing.GroupLayout

class ModifyConferenceView : ViewWithParams(APPLICATION_TITLE) {
    private val controller by inject<ModifyConferenceController>()

    private val LEFT_SIDE_WIDTH = 160.0

    override fun onParamSet() {
        controller.model.id.set(getParam<Int>("id") ?: 0)
    }

    override fun onDock() {
        super.onDock()
        controller.refreshData()
    }

    override fun onUndock() {
        super.onUndock()
        controller.model.reset()
    }

    private val progress = progressbar()

    override val root = vbox(alignment = Pos.CENTER) {
        maxWidth = 745.0

        progress.prefWidthProperty().bind(widthProperty())
        this += progress
        controller.model.isLoading.onChange {
            progress.opacity = if (it) 1.0 else 0.0
        }

        vbox(alignment = Pos.CENTER) {
            paddingAll = 32.0

            vbox(32.0) {
                text("Modify Conference") {
                    font = Font(24.0)
                }

                hbox(128.0) {
                    addNameAndDateFields()
                    addCoChairAndPCSelection()
                }

                hbox(16.0) {
                    maxHeight = 384.0

                    addSectionsList()
                    addSectionPane()
                }

                hbox {
                    alignment = Pos.CENTER_RIGHT

                    button("Close") {
                        action { switchTo(ConferenceListView::class) }
                    }
                }

            }
        }
    }

    private fun EventTarget.addNameAndDateFields() = vbox(16.0) {
        vbox {
            maxWidth = LEFT_SIDE_WIDTH

            label("Name")
            textfield(controller.model.name) {
                promptText = "Name"
                onBlur { controller.updateConference() }
            }
        }

        vbox(8.0) {
            text("Deadlines") {
                font = Font(18.0)
            }

            datePicker("Abstract paper deadline", controller.model.abstractDeadline, LEFT_SIDE_WIDTH) {
                onBlur { controller.updateConference() }
            }
            datePicker("Full paper deadline", controller.model.paperDeadline, LEFT_SIDE_WIDTH) {
                onBlur { controller.updateConference() }
            }
            datePicker("Bidding deadline", controller.model.biddingDeadline, LEFT_SIDE_WIDTH) {
                onBlur { controller.updateConference() }
            }
            datePicker("Review deadline", controller.model.reviewDeadline, LEFT_SIDE_WIDTH) {
                onBlur { controller.updateConference() }
            }
        }
    }

    private fun EventTarget.addCoChairAndPCSelection() = vbox(16.0) {
        vbox {
            maxWidth = 256.0

            label("Co-chair")
            combobox(controller.model.selectedChair, controller.model.sources.chairs) {
                minWidth = 256.0
                promptText = "Select a co-chair"
            }
        }
        vbox {
            maxWidth = 256.0

            label("Program committees")
            textfield(controller.model.search) {
                promptText = "Search"
            }
            listview(controller.model.searchedCommittees) {
                minWidth = 256.0
                maxHeight = 182.0
                cellFactory = CheckBoxListCell.forListView { it.selected }
            }
        }
    }

    private fun EventTarget.addSectionsList() = vbox(4.0) {
        hbox(LEFT_SIDE_WIDTH - 105.0) {
            text("Sections") {
                font = Font(18.0)
            }

            button("Save") {
                controller.model.selectedSection.onChange {
                    if (it == null) {
                        // When a list item is not in focus, prepare model for add
                        controller.model.selectedSection.set(ModifyConferenceSectionModel())
                        return@onChange
                    }
                }

                action { controller.saveSection() }
            }
        }

        listview(controller.model.sections) {
            maxWidth = LEFT_SIDE_WIDTH
            selectionModel.selectionMode = SelectionMode.SINGLE
            selectionModel.selectedItemProperty().addListener { _, _, value ->
                controller.model.selectedSection.set(value)
            }
        }
    }

    private fun EventTarget.addSectionPane() = vBoxPane(32.0) {
        controller.model.selectedSection.onChange {
            if (it == null) {
                return@onChange
            }

            children.clear()

            addPaneFieldsAndDeleteButton(it)
            addProposalsWithAddButton(it)
        }
    }

    private fun EventTarget.addPaneFieldsAndDeleteButton(section: ModifyConferenceSectionModel) = vbox(8.0) {
        hbox(49.0) {
            vbox(8.0) {
                vbox {
                    maxWidth = LEFT_SIDE_WIDTH

                    label("Name")
                    textfield(section.name) {
                        promptText = "Name"
                    }
                }
                datePicker("Start date", section.startDate, LEFT_SIDE_WIDTH)
            }
            vbox(8.0) {
                vbox {
                    maxWidth = LEFT_SIDE_WIDTH

                    label("Room")
                    combobox(section.selectedRoom, controller.model.sources.rooms) {
                        minWidth = LEFT_SIDE_WIDTH
                        promptText = "Select a room"
                    }
                }
                datePicker("End date", section.endDate, LEFT_SIDE_WIDTH)
            }
            button("Delete") {
                disableProperty().set(section.id.get() == 0)
                action { controller.deleteSection(section) }
            }
        }

        hbox(32.0, Pos.BOTTOM_LEFT) {
            vbox {
                label("Session chair")
                combobox(section.sessionChair, controller.model.sources.users) {
                    promptText = "Select a session chair"
                }
            }

            hbox(4.0, Pos.CENTER_LEFT) {
                paddingTop = 16.0

                label("Participants:")
                text(section.participants.get().toString()) {
                    font = Font(14.0)
                }
            }
        }

    }

    private fun EventTarget.addProposalsWithAddButton(section: ModifyConferenceSectionModel) =
        vbox(4.0) {
            hbox(370.0, Pos.BOTTOM_LEFT) {
                text("Proposals") {
                    font = Font(14.0)
                }
                button("Add") {
                    disableProperty().set(section.id.get() == 0)
                    action {
                        selectProposalDialog({ controller.getProposals() }) {
                            it?.let { controller.addProposal(it.id) }
                        }
                    }
                }
            }

            tableview(section.proposals) {
                readonlyColumn("Proposal name", ProposalItemModel::name).minWidth = 128.0
                readonlyColumn("Proposal authors", ProposalItemModel::authors).minWidth = 256.0
                readonlyColumn("Actions", ProposalItemModel::id).setNode {
                    button("Remove") {
                        action { controller.removeProposal(item) }
                    }
                }
            }
        }
}