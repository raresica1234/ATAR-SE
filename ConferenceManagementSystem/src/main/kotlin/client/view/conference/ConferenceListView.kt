package client.view.conference

import client.controller.conference.ConferenceListController
import client.model.conference.ConferenceListItemModel
import client.state.userState
import client.view.component.vBoxPane
import client.view.proposal.ViewProposalView
import client.view.proposal.SubmitProposalView
import client.view.room.ManageRoomsView
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.TabPane
import javafx.scene.layout.HBox
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.dateConverter
import utils.switchTo

class ConferenceListView : View(APPLICATION_TITLE) {
    private val SELECT_A_CONFERENCE = "Select a conference to view its data..."

    private val controller by inject<ConferenceListController>()

    private val activeListView = buildListView(controller.model.activeConferences)
    private val participatingListView = buildListView(controller.model.participatingConferences)

    private val submitProposalButton = button("Submit proposal") {
        action {
            switchTo(
                SubmitProposalView::class,
                SubmitProposalView.PARAM_CONFERENCE to controller.model.getConference()
            )
        }
    }

    private val participateButton = button("Participate") {
        action { println("Participate to ${controller.model.selectedConference.get()}") }
    }

    private val manageButton = button("Manage") {
        action { switchTo(ModifyConferenceView::class, "id" to controller.model.getConferenceId()) }
    }

    private val viewButton = button("View") {
        action {
            if (controller.isAuthor()) {
                switchTo(
                    ViewProposalView::class,
                    "userId" to userState.user.id,
                    "conferenceId" to controller.model.getConferenceId()
                )
            }
        }
    }

    private val progress = progressbar {
        maxWidth = 192.0
    }

    override fun onDock() {
        super.onDock()
        controller.refreshData()
    }

    override val root = vbox(32.0, Pos.CENTER) {
        minWidth = 768.0
        minHeight = 448.0
        paddingAll = 32.0

        hbox(364.0) {
            maxWidth = 768.0

            text("Conferences") {
                font = Font(24.0)
            }

            if (userState.user.isSiteAdministrator) {
                hbox(16.0, Pos.CENTER_RIGHT) {
                    button("Manage Rooms") {
                        minWidth = 128.0
                        action { switchTo(ManageRoomsView::class) }
                    }

                    button("Create Conference") {
                        minWidth = 128.0
                        action { switchTo(CreateConferenceView::class) }
                    }
                }
            }
        }

        hbox(32.0) {
            maxWidth = 768.0

            vbox {
                this += progress
                controller.model.isLoading.onChange {
                    children.remove(progress)
                    if (it) {
                        children.add(0, progress)
                    }
                }

                textfield(controller.model.search) {
                    promptText = "Search"
                }

                tabpane {
                    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                    selectionModel.selectedIndexProperty().onChange {
                        controller.model.selectedConference.set(null)
                        activeListView.selectionModel.clearSelection()
                        participatingListView.selectionModel.clearSelection()
                    }

                    tab("Active") {
                        this += activeListView
                    }
                    tab("Participating") {
                        this += participatingListView
                    }
                }
            }

            vBoxPane(16.0) {
                minWidth = 576.0

                text(SELECT_A_CONFERENCE) {
                    font = Font(18.0)

                    controller.model.selectedConference.onChange {
                        text = it?.conference?.name ?: SELECT_A_CONFERENCE
                    }
                }
                vbox(8.0) {
                    this += buildLabelWithData("Abstract paper deadline:") {
                        dateConverter.toString(it.conference.abstractDeadline).ifBlank { "None" }
                    }
                    this += buildLabelWithData("Full paper deadline:") {
                        dateConverter.toString(it.conference.paperDeadline).ifBlank { "None" }
                    }
                    this += buildLabelWithData("Bidding deadline:") {
                        dateConverter.toString(it.conference.biddingDeadline).ifBlank { "None" }
                    }
                    this += buildLabelWithData("Review deadline:") {
                        dateConverter.toString(it.conference.reviewDeadline).ifBlank { "None" }
                    }
                    this += buildLabelWithData("Sections:") { it.sections }
                    this += buildLabelWithData("Submitted papers:") { it.papers }

                    this += buildConferenceActions()
                }
            }
        }
    }

    private fun buildListView(items: ObservableList<ConferenceListItemModel>) = listview(items) {
        maxWidth = 192.0
        maxHeight = 256.0

        selectionModel.selectionMode = SelectionMode.SINGLE
        selectionModel.selectedItemProperty().addListener { _, _, value ->
            controller.model.selectedConference.set(value)
        }
    }

    private fun buildLabelWithData(labelText: String, extractor: (ConferenceListItemModel) -> String) = hbox(8.0) {
        label(labelText)
        text("-") {
            style { fontWeight = FontWeight.BOLD }

            controller.model.selectedConference.onChange {
                text = if (it == null) "-" else extractor(it)
            }
        }
    }

    private fun buildConferenceActions() = hbox(16.0, Pos.BOTTOM_RIGHT) {
        minWidth = 448.0
        paddingTop = 56.0

        controller.model.selectedConference.onChange {
            children.clear()

            if (it == null || userState.user.isSiteAdministrator) {
                return@onChange
            }

            if (controller.model.activeConferences.contains(it)) {
                buildActiveConferenceAction(this, it)
                return@onChange
            }

            buildPendingConferenceAction(this, it)
        }
    }

    private fun buildActiveConferenceAction(hbox: HBox, itemModel: ConferenceListItemModel) {
        if (!itemModel.hideExtraButton) {
            hbox += submitProposalButton
        }
        hbox += participateButton
    }

    private fun buildPendingConferenceAction(hbox: HBox, itemModel: ConferenceListItemModel) {
        if (!itemModel.hideExtraButton) {
            hbox += manageButton
        }
        hbox += viewButton
    }
}