package client.view

import client.controller.CreatePasswordController
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class CreatePasswordView() : View(APPLICATION_TITLE) {
    private val controller: CreatePasswordController by inject()


    override val root = vbox {
        alignment = Pos.CENTER
        minWidth = 320.0
        minHeight = 448.0
        paddingAll = 32.0

        text("Create password") {
            font = Font(24.0)
            paddingTop = 32.0
        }
        vbox {
            maxWidth = 192.0
            vbox {
                paddingTop = 32.0
                text("Your email:") {
                    font = Font(14.0)
                }

                text(controller.user.email) {
                    font = Font(14.0)
                }
            }

            vbox {
                paddingTop = 32.0

                label("Password")
                passwordfield {
                    promptText = "Password"
                    textProperty().addListener { _, _, newValue ->
                        controller.password = newValue
                    }
                }
            }
            vbox {
                paddingVertical = 32.0

                label("Confirm Password")
                passwordfield {
                    promptText = "Confirm Password"
                    textProperty().addListener { _, _, newValue ->
                        controller.confirmPasword = newValue
                    }
                }
            }

        }

        vbox {
            alignment = Pos.CENTER
            button("Create password") {
                action {
                    if (controller.handleCreatePassword()) {
                        switchTo(LoginView::class)
                    }
                }
            }
        }
    }
}