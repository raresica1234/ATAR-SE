package client.view

import client.controller.CreatePasswordController
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE

class CreatePasswordView : ViewWithParams(APPLICATION_TITLE) {
    companion object {
        const val PARAM_USER = "user"
    }

    private val controller: CreatePasswordController by inject()

    override fun onParamSet() {
        controller.model.user.set(getParam(PARAM_USER))
    }

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
                label("Your email:") {
                    font = Font(14.0)
                }

                text {
                    font = Font(14.0)
                    controller.model.user.addListener { _, _, newValue -> text = newValue.email }
                }
            }

            vbox {
                paddingTop = 32.0

                label("Password")
                passwordfield {
                    promptText = "Password"
                    textProperty().bindBidirectional(controller.model.password)
                }
            }
            vbox {
                paddingVertical = 32.0

                label("Confirm Password")
                passwordfield {
                    promptText = "Confirm Password"
                    textProperty().bindBidirectional(controller.model.confirmPasword)
                }
            }
        }

        vbox {
            alignment = Pos.CENTER
            button("Create password") {
                action {
                    if (controller.handleCreatePassword()) {
                        // TODO: Open browse conferences
                        println("Password modified successfully.")
                    }
                }
            }
        }
    }
}