package client.view.component

import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.scene.control.DatePicker
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import tornadofx.*
import utils.dateConverter
import java.time.LocalDate

fun EventTarget.datePicker(
    label: String,
    property: Property<LocalDate>,
    size: Double = 192.0,
    op: DatePicker.() -> Unit = {}
) = vbox().apply {
    maxWidth = size

    label(label)
    datepicker(property) {
        minWidth = size
        converter = dateConverter

        op(this)
    }
}

fun EventTarget.labelWithData(labelText: String, op: Text.() -> Unit = {}) = hbox(8.0) {
    label(labelText)
    textflow {
        maxWidth = 256.0

        text("-") {
            style { fontWeight = FontWeight.BOLD }

            op(this)
        }
    }
}