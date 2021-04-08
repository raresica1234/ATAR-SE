package utils

import javafx.beans.property.SimpleStringProperty
import tornadofx.Component
import tornadofx.UIComponent
import java.util.regex.Pattern
import kotlin.reflect.KClass

fun String.isEmail() = Pattern.compile(EMAIL_PATTERN)
    .matcher(this)
    .matches()

fun SimpleStringProperty.isNullOrBlank() = this.get().isNullOrBlank()

fun SimpleStringProperty.eq(other: SimpleStringProperty) = this.get() == other.get()

fun SimpleStringProperty.clear() = this.set(null)

fun <T : UIComponent> UIComponent.switchTo(component: KClass<T>) =
    this.replaceWith(component, sizeToScene = true, centerOnScreen = true)