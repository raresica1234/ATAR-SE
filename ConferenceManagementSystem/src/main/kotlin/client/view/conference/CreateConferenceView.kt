package client.view.conference

import client.controller.conference.CreateConferenceController
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.util.StringConverter
import tornadofx.*
import utils.APPLICATION_TITLE
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CreateConferenceView : View(APPLICATION_TITLE) {
    val controller by inject<CreateConferenceController>()

    val dateConverter = object : StringConverter<LocalDate?>() {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        override fun toString(date: LocalDate?) =
            if (date == null) "" else formatter.format(date)

        override fun fromString(string: String?) =
            if (string.isNullOrBlank()) null
            else LocalDate.parse(string, formatter)
    }

    override val root = vbox {
        alignment = Pos.CENTER
        minWidth = 256.0
        minHeight = 480.0
        paddingAll = 32.0
        spacing = 32.0

        text("Create Conference") {
            font = Font(24.0)
        }

        vbox {
            spacing = 8.0

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

            vbox {
                maxWidth = 192.0

                label("Abstract paper deadline")
                datepicker(controller.model.abstractDeadline) {
                    minWidth = 192.0
                    converter = dateConverter
                }
            }

            vbox {
                maxWidth = 192.0

                label("Full paper deadline")
                datepicker(controller.model.paperDeadline) {
                    minWidth = 192.0
                    converter = dateConverter
                }
            }

            vbox {
                maxWidth = 192.0

                label("Bidding deadline")
                datepicker(controller.model.biddingDeadline) {
                    minWidth = 192.0
                    converter = dateConverter
                }
            }

            vbox {
                maxWidth = 192.0

                label("Review deadline")
                datepicker(controller.model.reviewDeadline) {
                    minWidth = 192.0
                    converter = dateConverter
                }
            }

        }
        button("Create Conference") {
            action { println("create conference") }
        }
    }
}