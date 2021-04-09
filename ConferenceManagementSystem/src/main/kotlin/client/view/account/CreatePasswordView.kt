package client.view.account

import client.controller.account.CreatePasswordController
import client.view.ViewWithParams
import client.view.conference.ConferenceListView
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

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
                    controller.model.user.onChange { text = it?.email }
                }
            }

            vbox {
                paddingTop = 32.0

                label("Password")
                passwordfield(controller.model.password) {
                    promptText = "Password"
                }
            }
            vbox {
                paddingVertical = 32.0

                label("Confirm Password")
                passwordfield(controller.model.confirmPassword) {
                    promptText = "Confirm Password"
                }
            }
        }

        vbox {
            alignment = Pos.CENTER
            button("Create password") {
                action {
                    if (controller.handleCreatePassword()) {
                        switchTo(ConferenceListView::class)
                    }
                }
            }
        }
    }
}