package client.view

import client.controller.CreateAccountController
import javafx.geometry.Pos
import javafx.scene.control.ButtonType
import javafx.scene.text.Font
import tornadofx.*

class CreateAccountView : View("Conference Management System") {
    private val controller: CreateAccountController by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        minWidth = 960.0
        minHeight = 540.0
        paddingAll = 32.0

        vbox {
            maxWidth = 352.0
            text("Create account") {
                font = Font(24.0)
                paddingTop = 32.0
            }

            vbox {
                maxWidth = 192.0
                paddingTop = 32.0

                label("Email")
                textfield {
                    promptText = "Email"
                    textProperty().bindBidirectional(controller.createAccountModel.email)
                }
            }

            hbox {
                paddingTop = 16.0
                spacing = 16.0

                vbox {
                    minWidth = 192.0

                    label("Password")
                    textfield {
                        promptText = "Password"
                        textProperty().bindBidirectional(controller.createAccountModel.password)
                    }
                }

                vbox {
                    minWidth = 192.0

                    label("Confirm Password")
                    textfield {
                        promptText = "Confirm Password"
                        textProperty().bindBidirectional(controller.createAccountModel.confirmPassword)
                    }
                }
            }

            hbox {
                paddingTop = 16.0
                spacing = 16.0

                vbox {
                    minWidth = 192.0

                    label("First Name")
                    textfield {
                        promptText = "First Name"
                        textProperty().bindBidirectional(controller.createAccountModel.firstName)
                    }
                }

                vbox {
                    minWidth = 192.0

                    label("Last Name")
                    textfield {
                        promptText = "Last Name"
                        textProperty().bindBidirectional(controller.createAccountModel.lastName)
                    }
                }
            }

            hbox {
                paddingTop = 16.0
                spacing = 16.0

                vbox {
                    minWidth = 192.0

                    label("Affiliation")
                    textfield {
                        promptText = "Affiliation"
                        textProperty().bindBidirectional(controller.createAccountModel.affiliation)
                    }
                }

                vbox {
                    minWidth = 192.0

                    label("Webpage Link (Optional)")
                    textfield {
                        promptText = "Webpage Link"
                        textProperty().bindBidirectional(controller.createAccountModel.webpageLink)
                    }
                }
            }

            hbox {
                paddingTop = 32.0
                alignment = Pos.CENTER
                spacing = 16.0

                button("Back") {
                    action { replaceWith(LoginView::class) }
                }
                button("Create account") {
                    action {
                        if (controller.handleCreateAccountClick()) {
                            information(
                                "Account created",
                                "Account created successfully, please log in into your new account",
                                ButtonType.OK
                            ) {
                                replaceWith(LoginView::class)
                            }
                        }
                    }
                }

            }
        }
    }
}