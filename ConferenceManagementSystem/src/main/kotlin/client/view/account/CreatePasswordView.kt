package client.view.account

import client.controller.account.CreatePasswordController
import client.view.ViewWithParams
import client.view.component.confirmPayment
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

    override val root = vbox(64.0, Pos.CENTER) {
        minWidth = 320.0
        minHeight = 448.0
        paddingAll = 32.0

        text("Create password") {
            font = Font(24.0)
        }

        vbox(16.0) {
            maxWidth = 192.0
            vbox {
                label("Your email:") {
                    font = Font(14.0)
                }

                text {
                    font = Font(14.0)
                    controller.model.user.onChange { text = it?.email }
                }
            }

            vbox {
                label("Password")
                passwordfield(controller.model.password) {
                    promptText = "Password"
                }
            }
            vbox {
                label("Confirm Password")
                passwordfield(controller.model.confirmPassword) {
                    promptText = "Confirm Password"
                }
            }
        }

        vbox(alignment = Pos.CENTER) {
            button("Create password") {
                confirmPayment {
                    if (controller.handleCreatePassword()) {
                        switchTo(ConferenceListView::class)
                    }
                }
            }
        }
    }
}