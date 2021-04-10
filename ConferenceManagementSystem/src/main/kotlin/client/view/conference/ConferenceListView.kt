package client.view.conference

import client.controller.conference.ConferenceListController
import client.model.conference.ConferenceListItemModel
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.TabPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.Stylesheet.Companion.button
import utils.APPLICATION_TITLE

class ConferenceListView : View(APPLICATION_TITLE) {
    private val SELECT_A_CONFERENCE = "Select a conference to view its data..."

    private val controller by inject<ConferenceListController>()

    private val activeListView = buildListView(controller.conferenceListModel.activeConferences)
    private val participatingListView = buildListView(controller.conferenceListModel.participatingConferences)

    private val submitProposalButton = buildConferenceButton("Submit proposal") {
        println("Submit proposal for ${controller.conferenceListModel.selectedConference.get()}")
    }

    private val participateButton = buildConferenceButton("Participate") {
        println("Participate to ${controller.conferenceListModel.selectedConference.get()}")
    }

    private val manageButton = buildConferenceButton("Manage") {
        println("Manage ${controller.conferenceListModel.selectedConference.get()}")
    }

    private val viewButton = buildConferenceButton("View") {
        println("View ${controller.conferenceListModel.selectedConference.get()}")
    }

    override fun onBeforeShow() {
        super.onBeforeShow()
        controller.handleOnUndock()
    }

    override val root = vbox {
        alignment = Pos.CENTER
        minWidth = 768.0
        minHeight = 448.0
        paddingAll = 32.0
        spacing = 32.0

        hbox {
            spacing = 416.0
            maxWidth = 768.0

            text("Conferences") {
                font = Font(24.0)
            }

            hbox {
                alignment = Pos.CENTER_RIGHT
                spacing = 16.0

                button("Manage Rooms") {
                    action { println("Go to manage rooms!") }
                }

                button("Add conference") {
                    action { println("Go to add conference!") }
                }
            }
        }

        hbox {
            spacing = 32.0
            maxWidth = 768.0

            vbox {
                textfield(controller.conferenceListModel.search) {
                    promptText = "Search"
                }

                tabpane {
                    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                    selectionModel.selectedIndexProperty().onChange {
                        controller.conferenceListModel.selectedConference.set(null)
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

            vbox {
                style {
                    backgroundColor += c("#fafafa")
                    borderColor += box(c("#222"))
                    minWidth = 576.px
                    padding = box(16.px)
                    spacing = 16.px
                }

                text(SELECT_A_CONFERENCE) {
                    font = Font(18.0)

                    controller.conferenceListModel.selectedConference.onChange {
                        text = it?.conference?.name ?: SELECT_A_CONFERENCE
                    }
                }
                vbox {
                    spacing = 8.0

                    this += buildLabelWithData("Abstract paper deadline:") { it.conference.abstractDeadline.toString() }
                    this += buildLabelWithData("Full paper deadline:") { it.conference.paperDeadline.toString() }
                    this += buildLabelWithData("Bidding deadline:") { it.conference.biddingDeadline.toString() }
                    this += buildLabelWithData("Review deadline:") { it.conference.reviewDeadline.toString() }
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
            controller.conferenceListModel.selectedConference.set(value)
        }
    }

    private fun buildLabelWithData(labelText: String, extractor: (ConferenceListItemModel) -> String) = hbox {
        spacing = 8.0

        label(labelText)
        text("-") {
            style { fontWeight = FontWeight.BOLD }

            controller.conferenceListModel.selectedConference.onChange {
                text = if (it == null) "-" else extractor(it)
            }
        }
    }

    private fun buildConferenceActions() = hbox {
        minWidth = 448.0
        spacing = 16.0
        paddingTop = 56.0
        alignment = Pos.BOTTOM_RIGHT

        controller.conferenceListModel.selectedConference.addListener { _, oldValue, newValue ->
            if (newValue == null) {
                children.clear()
                return@addListener
            }

            val isActive = controller.conferenceListModel.activeConferences.contains(newValue)

            if (controller.conferenceListModel.activeConferences.contains(oldValue) && isActive) {
                return@addListener
            }

            children.clear()

            if (isActive) {
                this += submitProposalButton
                this += participateButton
                return@addListener
            }

            if (newValue.conference.name == "Conference 7") {
                this += manageButton
            }
            this += viewButton
        }
    }

    private fun buildConferenceButton(text: String, onClick: () -> Unit) = button(text) {
        action { onClick() }
    }
}