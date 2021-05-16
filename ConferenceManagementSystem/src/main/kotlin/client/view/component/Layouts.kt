package client.view.component

import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.layout.VBox
import javafx.util.Callback
import tornadofx.*

fun EventTarget.vBoxPane(spacing: Double? = null, op: VBox.() -> Unit = {}) = vbox(spacing).apply {
    style {
        backgroundColor += c("#fafafa")
        borderColor += box(c("#222"))
        padding = box(16.px)
    }
    op(this)
}

fun <S, T> TableColumn<S, T>.setNode(op: HBoxWithItem<T>.() -> Unit = {}) {
    cellFactory = Callback<TableColumn<S, T>, TableCell<S, T>> {
        object : TableCell<S, T>() {
            override fun updateItem(item: T, empty: Boolean) {
                super.updateItem(item, empty)
                graphic = if (empty) null else hboxWithItem(item, 16.0, Pos.CENTER).apply { op(this) }
            }
        }
    }
}

fun <T> ListView<T>.renderItem(onRender: (T) -> Node) {
    cellFactory = Callback<ListView<T>, ListCell<T>> {
        object : ListCell<T>() {
            override fun updateItem(item: T, empty: Boolean) {
                super.updateItem(item, empty)
                graphic = if (empty) null else onRender(item)
            }
        }
    }
}