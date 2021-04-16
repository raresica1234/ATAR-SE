package client.view.component

import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.scene.control.DatePicker
import tornadofx.datepicker
import tornadofx.label
import tornadofx.vbox
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