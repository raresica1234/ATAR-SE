package utils

import client.view.ViewWithParams
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.scene.Node
import javafx.scene.control.MultipleSelectionModel
import javafx.util.StringConverter
import tornadofx.UIComponent
import tornadofx.observableListOf
import tornadofx.onChange
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern
import kotlin.reflect.KClass

fun String.isEmail() = Pattern.compile(EMAIL_PATTERN)
    .matcher(this)
    .matches()

fun SimpleStringProperty.isNullOrBlank() = this.get().isNullOrBlank()

fun SimpleStringProperty.isValid() = !this.isNullOrBlank()

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

fun <T> SimpleObjectProperty<T>.isValid() = this.get() != null

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
        "The date ${this.format()} should be before the date ${other.format()}"
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

val dateTimeConverter = object : StringConverter<LocalDateTime?>() {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")

    override fun toString(date: LocalDateTime?) =
        if (date == null) "" else formatter.format(date)

    override fun fromString(string: String?) =
        if (string.isNullOrBlank()) null
        else LocalDateTime.parse(string, formatter)
}

fun Node.onBlur(handler: () -> Unit) = focusedProperty().onChange {
    if (!it) {
        handler()
    }
}

fun <T> ObservableList<T>.setObject(oldItem: T, newItem: T?) {
    set(indexOf(oldItem), newItem)
}

fun <T> T?.ifNull(provider: () -> T) = this ?: provider()

fun Int.hasSameSign(other: Int) = this * other > 0

fun SimpleIntegerProperty?.isNull() = this?.get() == null

fun LocalDate?.format(): String = dateConverter.toString(this)

fun LocalDateTime?.format(): String = dateTimeConverter.toString(this)

infix fun SimpleIntegerProperty.eq(other: SimpleIntegerProperty) = this.get() == other.get()

fun <T> getNoSelectionMode() = object : MultipleSelectionModel<T>() {
    override fun clearAndSelect(p0: Int) {
    }

    override fun select(p0: Int) {
    }

    override fun select(p0: T) {
    }

    override fun clearSelection(p0: Int) {
    }

    override fun clearSelection() {
    }

    override fun isSelected(p0: Int) = false

    override fun isEmpty() = true

    override fun selectPrevious() {
    }

    override fun selectNext() {
    }

    override fun selectFirst() {
    }

    override fun selectLast() {
    }

    override fun getSelectedIndices() = observableListOf<Int>()

    override fun getSelectedItems() = observableListOf<T>()

    override fun selectIndices(p0: Int, vararg p1: Int) {
    }

    override fun selectAll() {
    }
}