package client.view.component

import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.scene.control.DatePicker
import tornadofx.datepicker
import tornadofx.label
import tornadofx.vbox
import utils.dateConverter
import java.time.LocalDate

fun EventTarget.datePicker(label: String, property: Property<LocalDate>, op: DatePicker.() -> Unit = {}) =
    vbox().apply {
        maxWidth = 192.0

        label(label)
        datepicker(property) {
            minWidth = 192.0
            converter = dateConverter

            op(this)
        }
    }