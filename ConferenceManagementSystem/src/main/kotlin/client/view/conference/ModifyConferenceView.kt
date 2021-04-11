package client.view.conference

import client.controller.conference.ModifyConferenceController
import client.view.ViewWithParams
import client.view.component.datePicker
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE

class ModifyConferenceView : ViewWithParams(APPLICATION_TITLE) {
    private val controller by inject<ModifyConferenceController>()

    override fun onParamSet() {
        TODO("Not yet implemented")
    }

    override val root = vbox(alignment = Pos.CENTER) {
        paddingAll = 32.0

        vbox(32.0) {
            text("Modify Conference") {
                font = Font(24.0)
            }

            hbox(128.0) {
                vbox(16.0) {
                    vbox {
                        maxWidth = 192.0

                        label("Name")
                        textfield() {
                            promptText = "Name"
                        }
                    }

                    vbox(8.0) {
                        text("Deadlines") {
                            font = Font(18.0)
                        }

                        datePicker("Abstract paper deadline", SimpleObjectProperty())
                        datePicker("Full paper deadline", SimpleObjectProperty())
                        datePicker("Bidding deadline", SimpleObjectProperty())
                        datePicker("Review deadline", SimpleObjectProperty())
                    }
                }
            }
        }
    }
}