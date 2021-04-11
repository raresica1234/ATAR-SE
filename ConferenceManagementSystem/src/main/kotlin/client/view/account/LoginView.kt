package client.view.account

import client.controller.account.LoginController
import client.view.conference.ConferenceListView
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class LoginView : View(APPLICATION_TITLE) {
    private val controller: LoginController by inject()

    override val root = vbox(32.0, alignment = Pos.CENTER) {
        minWidth = 320.0
        minHeight = 448.0
        paddingAll = 32.0

        text("Log in") {
            font = Font(24.0)
        }

        vbox(16.0, Pos.CENTER) {
            vbox {
                maxWidth = 192.0

                label("Email")
                textfield(controller.model.email) {
                    promptText = "Email"
                }
            }
            vbox {
                maxWidth = 192.0

                label("Password")
                passwordfield(controller.model.password) {
                    promptText = "Password"
                }
            }
            button("Log in") {
                action {
                    controller.handleLoginClick()?.let {
                        if (it.password.isEmpty()) {
                            switchTo(CreatePasswordView::class, CreatePasswordView.PARAM_USER to it)
                        } else {
                            switchTo(ConferenceListView::class)
                        }
                    }
                }
            }
        }

        vbox(8.0, Pos.CENTER) {
            maxWidth = 128.0

            text("You don't have an account?")
            button("Create account") {
                action { switchTo(CreateAccountView::class) }
            }
        }
    }
}