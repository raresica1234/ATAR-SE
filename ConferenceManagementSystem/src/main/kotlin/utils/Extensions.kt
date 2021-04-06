package utils

import javafx.beans.property.SimpleStringProperty
import java.util.regex.Pattern

fun String.isEmail() = Pattern.compile(EMAIL_PATTERN)
    .matcher(this)
    .matches()

fun SimpleStringProperty.isNullOrBlank() = this.get().isNullOrBlank()

fun SimpleStringProperty.eq(other: SimpleStringProperty) = this.get() == other.get()