package utils

import javafx.scene.control.ButtonType

class ValidationException(
    val title: String = "Unknown error",
    message: String = "Unknown validation error",
) : RuntimeException(message) {
    fun displayError() = tornadofx.error(title, message, ButtonType.OK)
}