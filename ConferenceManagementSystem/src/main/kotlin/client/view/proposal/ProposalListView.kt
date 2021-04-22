package client.view.proposal

import client.controller.proposal.ProposalListController
import client.model.proposal.ProposalListItemModel
import client.view.ViewWithParams
import client.view.component.labelWithData
import client.view.component.vBoxPane
import client.view.conference.ConferenceListView
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.TabPane
import javafx.scene.text.Font
import server.domain.Conference
import server.domain.RoleType
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class ProposalListView : ViewWithParams(APPLICATION_TITLE) {
    private val LIST_WIDTH = 192.0
    private val SELECT_A_PROPOSAL = "Select a proposal to view its data..."

    private val controller by inject<ProposalListController>()

    override fun onParamSet() {
        getParam<Conference>("conference")?.let { controller.onParamsSet(it) }
    }

    private val progress = progressbar {
        maxWidth = LIST_WIDTH
    }

    private val leftListView = buildListView(controller.model.leftTabProposals)
    private val rightListView = buildListView(controller.model.rightTabProposals)

    override val root = vbox(alignment = Pos.CENTER) {
        paddingAll = 32.0

        vbox(32.0) {
            text("Proposals") {
                font = Font(24.0)

                controller.model.conference.onChange {
                    text = "${it?.name} - Proposals"
                }
            }

            hbox(32.0) {
                vbox {
                    this += progress
                    controller.model.isLoading.onChange {
                        children.remove(progress)
                        if (it) {
                            children.add(0, progress)
                        }
                    }

                    tabpane {
                        maxWidth = LIST_WIDTH
                        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                        selectionModel.selectedIndexProperty().onChange {
                            controller.model.selectedProposal.set(null)
                            leftListView.selectionModel.clearSelection()
                            rightListView.selectionModel.clearSelection()
                        }

                        tab("For bidding") {
                            maxWidth = LIST_WIDTH

                            controller.model.role.onChange {
                                text = when (it) {
                                    RoleType.PROGRAM_COMMITTEE -> "For bidding"
                                    RoleType.CHAIR -> "All proposals"
                                    else -> ""
                                }
                            }

                            this += leftListView
                        }
                        tab("For reviewing") {
                            maxWidth = LIST_WIDTH

                            controller.model.role.onChange {
                                text = when (it) {
                                    RoleType.PROGRAM_COMMITTEE -> "For reviewing"
                                    RoleType.CHAIR -> "Conflicts"
                                    else -> ""
                                }
                            }

                            this += rightListView
                        }
                    }
                }

                vBoxPane(16.0) {
                    minWidth = 512.0

                    text(SELECT_A_PROPOSAL) {
                        font = Font(18.0)

                        controller.model.selectedProposal.onChange {
                            text = it?.name ?: SELECT_A_PROPOSAL
                        }
                    }
                    vbox(8.0) {
                        buildLabelWithData("Name:") { it.name }
                        buildLabelWithData("Topics:") { it.topics }
                        buildLabelWithData("Keywords:") { it.keywords }
                        buildLabelWithData("Authors:") { it.authors }
                        buildLabelWithData("Abstract:") { it.abstract }
                        buildLabelWithData("Status:") { it.status }
                    }

                    buildProposalActions()
                }
            }
            hbox(alignment = Pos.CENTER_RIGHT) {
                button("Back") {
                    action {
                        switchTo(ConferenceListView::class)
                    }
                }
            }
        }
    }

    private fun buildListView(items: ObservableList<ProposalListItemModel>) = listview(items) {
        maxWidth = LIST_WIDTH
        maxHeight = 256.0

        selectionModel.selectionMode = SelectionMode.SINGLE
        selectionModel.selectedItemProperty().onChange {
            controller.model.selectedProposal.set(it)
        }
    }

    private fun EventTarget.buildLabelWithData(labelText: String, extractor: (ProposalListItemModel) -> String) =
        labelWithData(labelText) {
            controller.model.selectedProposal.onChange {
                text = if (it == null) "-" else extractor(it)

            }
        }

    private fun EventTarget.buildProposalActions() = hbox(16.0, Pos.CENTER_RIGHT) {
        controller.model.selectedProposal.onChange {
            children.clear()

            if (it == null) {
                return@onChange
            }

            if (controller.model.leftTabProposals.contains(it)) {
                buildLeftTabActions(it)
                return@onChange
            }

            buildRightTabActions(it)
        }
    }

    private fun EventTarget.buildLeftTabActions(proposal: ProposalListItemModel) {
        if (controller.model.role.get() == RoleType.CHAIR) {
            button("Manage reviews") {
                action { println("Manage reviews for proposal ${proposal.id}") }
            }
            return
        }
        button("Refuse to review") {
            action { println("Refuse to review for proposal ${proposal.id}") }
        }
        button("Could review") {
            action { println("Could review for proposal ${proposal.id}") }
        }
        button("Pleased to review") {
            action { println("Pleased to review for proposal ${proposal.id}") }
        }
    }

    private fun EventTarget.buildRightTabActions(proposal: ProposalListItemModel) {
        if (controller.model.role.get() == RoleType.CHAIR) {
            button("Resolve conflicts") {
                action { println("Resolve conflicts for proposal ${proposal.id}") }
            }
            return
        }
        button("Review") {
            action { println("Review for proposal ${proposal.id}") }
        }
    }
}