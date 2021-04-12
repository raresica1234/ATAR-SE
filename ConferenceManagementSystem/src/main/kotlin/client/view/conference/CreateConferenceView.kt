package client.view.conference

import client.controller.conference.CreateConferenceController
import client.view.component.datePicker
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class CreateConferenceView : View(APPLICATION_TITLE) {
    private val controller by inject<CreateConferenceController>()

    override fun onUndock() {
        super.onUndock()
        controller.model.clear()
    }

    override fun onDock() {
        super.onDock()
        controller.refreshChairs()
    }

    override val root = vbox(32.0, Pos.CENTER) {
        minWidth = 320.0
        minHeight = 512.0
        paddingAll = 32.0

        text("Create Conference") {
            font = Font(24.0)
        }

        vbox(8.0, Pos.CENTER) {
            vbox {
                maxWidth = 192.0

                label("Name")
                textfield(controller.model.name) {
                    promptText = "Name"
                }
            }

            vbox {
                maxWidth = 192.0

                label("Chair")
                combobox(controller.model.chair, controller.model.chairs) {
                    minWidth = 192.0
                    promptText = "Select a chair"
                }
            }

            datePicker("Abstract paper deadline", controller.model.abstractDeadline)
            datePicker("Full paper deadline", controller.model.paperDeadline)
            datePicker("Bidding deadline", controller.model.biddingDeadline)
            datePicker("Review deadline", controller.model.reviewDeadline)
        }

        hbox(16.0, Pos.CENTER) {
            button("Back") {
                action {
                    switchTo(ConferenceListView::class)
                }
            }
            button("Create Conference") {
                action {
                    if (controller.handleCreateConferenceClick()) {
                        switchTo(ConferenceListView::class)
                    }
                }
            }
        }
    }
}