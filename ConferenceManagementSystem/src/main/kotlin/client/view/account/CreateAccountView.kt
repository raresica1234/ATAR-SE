package client.view.account

import client.controller.account.CreateAccountController
import javafx.geometry.Pos
import javafx.scene.control.ButtonType
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class CreateAccountView : View(APPLICATION_TITLE) {
    private val controller: CreateAccountController by inject()

    override fun onUndock() {
        super.onUndock()
        controller.model.clear()
    }

    override val root = vbox(32.0, Pos.CENTER) {
        minWidth = 512.0
        minHeight = 448.0

        vbox {
            maxWidth = 352.0

            text("Create account") {
                font = Font(24.0)
            }

            vbox {
                maxWidth = 192.0
                paddingTop = 32.0

                label("Email")
                textfield(controller.model.email) {
                    promptText = "Email"
                }
            }

            hbox(16.0) {
                paddingTop = 16.0

                vbox {
                    minWidth = 192.0

                    label("Password")
                    passwordfield(controller.model.password) {
                        promptText = "Password"
                    }
                }

                vbox {
                    minWidth = 192.0

                    label("Confirm Password")
                    passwordfield(controller.model.confirmPassword) {
                        promptText = "Confirm Password"
                    }
                }
            }

            hbox(16.0) {
                paddingTop = 16.0

                vbox {
                    minWidth = 192.0

                    label("First Name")
                    textfield(controller.model.firstName) {
                        promptText = "First Name"
                    }
                }

                vbox {
                    minWidth = 192.0

                    label("Last Name")
                    textfield(controller.model.lastName) {
                        promptText = "Last Name"
                    }
                }
            }

            hbox(16.0) {
                paddingTop = 16.0

                vbox {
                    minWidth = 192.0

                    label("Affiliation")
                    textfield(controller.model.affiliation) {
                        promptText = "Affiliation"
                    }
                }

                vbox {
                    minWidth = 192.0

                    label("Webpage Link (Optional)")
                    textfield(controller.model.webpageLink) {
                        promptText = "Webpage Link"
                    }
                }
            }

            hbox(16.0, Pos.CENTER) {
                paddingTop = 32.0

                button("Back") {
                    action { switchTo(LoginView::class) }
                }
                button("Create account") {
                    action {
                        if (controller.handleCreateAccountClick()) {
                            information(
                                "Account created",
                                "Account created successfully, please log in into your new account",
                                ButtonType.OK
                            ) {
                                switchTo(LoginView::class)
                            }
                        }
                    }
                }
            }
        }
    }
}