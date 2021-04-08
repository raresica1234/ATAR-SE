package utils

class ValidationException(
    val title: String = "Unknown error",
    message: String = "Unknown validation error",
) : RuntimeException(message) {
}