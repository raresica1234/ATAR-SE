package client.view.proposal

import javafx.beans.property.SimpleStringProperty
import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

private val PAPER_FILTERS = arrayOf(
    FileChooser.ExtensionFilter(
        "Full paper (*.pdf, *.docx, *.txt)",
        "*.pdf", "*.docx", "*.txt"
    )
)

fun EventTarget.uploadPaper(
    bindingName: SimpleStringProperty,
    onUpload: (File?) -> Unit = {}
) = hbox(32.0) {
    maxWidth = 416.0
    hbox(8.0, Pos.CENTER) {
        label("Full paper:") {
            minWidth = 70.0
        }

        label(bindingName) {
            minWidth = 180.0
            maxWidth = 180.0
        }
    }
    button("Upload") {
        minWidth = 128.0
        action {
            onUpload(chooseFile("Select full paper location", PAPER_FILTERS).first())
        }
    }
}