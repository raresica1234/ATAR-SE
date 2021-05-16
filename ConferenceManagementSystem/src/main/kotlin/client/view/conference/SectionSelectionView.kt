package client.view.conference

import client.controller.conference.SectionSelectionController
import client.view.ViewWithParams
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.switchTo

class SectionSelectionView : ViewWithParams() {
    val controller by inject<SectionSelectionController>()

    override fun onParamSet() {
        controller.onParamsSet(getParam<Int>("conferenceId") ?: 0)
    }

    override val root = vbox(alignment = Pos.CENTER) {
        vbox(32.0, Pos.CENTER) {
            paddingAll = 32.0

            text("Section selection") {
                font = Font(24.0)
            }
            listview(controller.model.sections) {
                minWidth = 256.0
                maxHeight = 512.0
                selectionModel.selectedItemProperty().onChange {
                    controller.model.selectedSection.set(it)
                }
            }
            hbox(32.0, Pos.CENTER) {
                button("Back") {
                    action { switchTo(ConferenceListView::class) }
                }
                button("Continue") {
                    action {
                        controller.onContinue()
                        switchTo(ConferenceListView::class)
                    }
                }
            }
        }
    }
}