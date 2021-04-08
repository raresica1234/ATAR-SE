package client.view

import client.controller.LoginController
import client.model.LoginState
import javafx.geometry.Pos
import javafx.scene.text.Font
import tornadofx.*
import utils.APPLICATION_TITLE
import utils.switchTo

class LoginView : View(APPLICATION_TITLE) {
    private val controller: LoginController by inject()

    override val root = vbox {
        alignment = Pos.CENTER
        minWidth = 320.0
        minHeight = 448.0
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
                    switchTo(CreatePasswordView::class)
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
                action { switchTo(CreateAccountView::class) }
            }
        }
    }
}