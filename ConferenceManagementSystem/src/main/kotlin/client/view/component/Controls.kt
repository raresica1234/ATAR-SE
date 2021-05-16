package client.view.component

import javafx.beans.property.Property
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventTarget
import javafx.scene.control.Button
import javafx.scene.control.DatePicker
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import tornadofx.*
import utils.TICKET_PRICE
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

fun EventTarget.labelWithData(
    labelText: String,
    binding: SimpleStringProperty = SimpleStringProperty("-"),
    op: Text.() -> Unit = {}
) = hbox(8.0) {
    label(labelText)
    textflow {
        maxWidth = 256.0

        text(binding) {
            style { fontWeight = FontWeight.BOLD }

            op(this)
        }
    }
}

fun Button.confirmPayment(onConfirm: () -> Unit = {}) = action {
    confirm(
        "Participation payment",
        "To participate to this conference you have to pay for your ticket. " +
                "The cost of the conference is $TICKET_PRICE RON and this amount will be withdrawn from your credit card.\n\n" +
                "Do you wish to continue?",
        actionFn = onConfirm
    )
}