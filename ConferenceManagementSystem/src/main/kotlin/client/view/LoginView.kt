package client.view

import client.controller.LoginController
import client.model.LoginState
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*

class LoginView : View("Conference Management System") {
    private val controller: LoginController by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        minWidth = 960.0
        minHeight = 540.0
        paddingAll = 32.0

        text("Log in") {
            font = Font(24.0)
            paddingTop = 32.0
        }

        vbox {
            maxWidth = 192.0
            paddingTop = 32.0

            label("Email")
            textfield {
                promptText = "Email"
                textProperty().addListener { _, _, newValue ->
                    controller.emailText = newValue
                }
            }
        }
        vbox {
            maxWidth = 192.0
            paddingVertical = 32.0

            label("Password")
            passwordfield {
                promptText = "Password"
                textProperty().addListener { _, _, newValue ->
                    controller.passwordText = newValue
                }
            }
        }
        button("Log in") {
            action {
                val loginState = controller.handleLoginClick()
                if (loginState == LoginState.LOGIN_USER) {
                    // TODO: Open the Browse Conference window
                    println("Login successful")
                }
                else if (loginState == LoginState.LOGIN_GUEST_USER) {
                    // TODO: Open the create password dialog
                    println("Login as guest user")
                }
            }
        }

        vbox {
            maxWidth = 128.0
            paddingTop = 32.0
            alignment = Pos.CENTER
            spacing = 8.0

            text("You don't have an account?")
            button("Create account") {
                action { replaceWith(CreateAccountView::class) }
            }
        }
    }
}