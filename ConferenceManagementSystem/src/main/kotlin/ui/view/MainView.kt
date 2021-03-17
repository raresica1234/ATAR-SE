package ui.view

import tornadofx.*
import ui.controller.UIController

class MainView : View("Conference Management System") {
    val controller: UIController by inject()

    override val root = vbox {
        label(title)
        button("Press me")
        listview(controller.values)
    }
}