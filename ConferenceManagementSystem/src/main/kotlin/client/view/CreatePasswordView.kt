package client.view

import client.controller.CreatePasswordController
import javafx.geometry.Pos
import javafx.scene.text.Font
import server.domain.User
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class CreatePasswordView() : ViewWithParams(APPLICATION_TITLE) {
    companion object {
        const val PARAM_USER = "user"
    }

    private val controller: CreatePasswordController by inject()

    override fun onParamSet(params: Map<String, Any?>) {
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
                text("Your email:") {
                    font = Font(14.0)
                }

                text {
                    font = Font(14.0)
//                    textProperty().bindBidirectional(controller.model.user.)
//                    textProperty().bind(Bindings.createStringBinding({ controller.model.user }))
//                    controller.model.user.stringBinding(textProperty(), op = { it?.email })
                    controller.model.user.addListener { textProperty().set(it.email) }
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
                        switchTo(LoginView::class)
                    }
                }
            }
        }
    }
}