package client.controller

import tornadofx.Controller

class LoginController(var emailText: String = "", var passwordText: String = ""): Controller() {
    fun handleLoginClick() {
        println("Email: $emailText\nPassword: $passwordText")
    }

    fun handleCreateAccountClick() {
        println("Create account has been clicked")
    }
}