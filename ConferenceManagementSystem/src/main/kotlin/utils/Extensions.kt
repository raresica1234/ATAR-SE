package utils

import client.view.ViewWithParams
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.util.StringConverter
import tornadofx.UIComponent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern
import kotlin.reflect.KClass

fun String.isEmail() = Pattern.compile(EMAIL_PATTERN)
    .matcher(this)
    .matches()

fun SimpleStringProperty.isNullOrBlank() = this.get().isNullOrBlank()
fun SimpleStringProperty.getOrEmpty() = this.get().orEmpty()

fun SimpleStringProperty.eq(other: SimpleStringProperty) = this.get() == other.get()

fun SimpleStringProperty.clear() = this.set(null)

fun <T : UIComponent> UIComponent.switchTo(component: KClass<T>) =
    this.replaceWith(component, sizeToScene = true, centerOnScreen = true)

fun <T : ViewWithParams> UIComponent.switchTo(component: KClass<T>, vararg params: Pair<String, Any?>) {
    this.find(component).setParams(*params)
    this.replaceWith(component, sizeToScene = true, centerOnScreen = true)
}

fun <T> Iterable<T>.joinOrDefault(delimiter: String, default: String) =
    this.joinToString(delimiter).ifEmpty { default }

fun LocalDate.hasPassed() = this.isBefore(LocalDate.now())

fun <T> SimpleObjectProperty<T>.isObjectNull() = this.get() == null

/**
 * Validation for consecutive dates, returns the last valid date or null.
 * If the order is not respected validation exception is thrown.
 */
fun LocalDate?.validateBefore(other: LocalDate?, canBeEqual: Boolean = false): LocalDate? {
    if (this == null && other == null) {
        return null
    }
    if (other == null) {
        return this
    }
    if (this == null || (canBeEqual && this <= other) || this < other) {
        return other
    }
    throw ValidationException(
        "The dates are not in order",
        "The date ${dateConverter.toString(this)} should be before the date ${dateConverter.toString(other)}"
    )
}

fun <T> SimpleObjectProperty<T>.clear() = this.set(null)

val dateConverter = object : StringConverter<LocalDate?>() {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    override fun toString(date: LocalDate?) =
        if (date == null) "" else formatter.format(date)

    override fun fromString(string: String?) =
        if (string.isNullOrBlank()) null
        else LocalDate.parse(string, formatter)
}
