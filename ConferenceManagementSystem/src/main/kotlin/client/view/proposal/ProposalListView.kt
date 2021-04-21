package client.view.proposal

import client.controller.proposal.ProposalListController
import client.model.conference.ConferenceListItemModel
import client.model.proposal.ProposalListItemModel
import client.view.ViewWithParams
import client.view.component.vBoxPane
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.TabPane
import javafx.scene.text.Font
import server.domain.RoleType
import tornadofx.*
import utils.APPLICATION_TITLE

class ProposalListView : ViewWithParams(APPLICATION_TITLE) {
    private val LIST_WIDTH = 192.0
    private val SELECT_A_PROPOSAL = "Select a proposal to view its data..."

    private val controller by inject<ProposalListController>()

    override fun onParamSet() {
        controller.onParamsSet(getParam("conferenceId") ?: 0)
    }

    private val progress = progressbar {
        maxWidth = LIST_WIDTH
    }

    private val leftListView = buildListView(controller.model.leftTabProposals)
    private val rightListView = buildListView(controller.model.rightTabProposals)

    override val root = vbox(32.0, Pos.CENTER) {
        paddingAll = 32.0

        text("Proposals") {
            font = Font(24.0)

            controller.model.conference.onChange {
                text = "${it?.name} - Proposals"
            }
        }

        hbox(32.0) {
            this += progress
            controller.model.isLoading.onChange {
                children.remove(progress)
                if (it) {
                    children.add(0, progress)
                }
            }

            tabpane {
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                selectionModel.selectedIndexProperty().onChange {
                    controller.model.selectedProposal.set(null)
                    leftListView.selectionModel.clearSelection()
                    rightListView.selectionModel.clearSelection()
                }

                tab("For bidding") {
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

            vBoxPane(16.0) {
                text(SELECT_A_PROPOSAL) {
                    font = Font(18.0)

                    controller.model.selectedProposal.onChange {
                        text = it?.name ?: SELECT_A_PROPOSAL
                    }
                    vbox(8.0) {
                        TODO("Add labels")
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
}