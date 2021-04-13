package client.view.component

import javafx.event.EventTarget
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.layout.HBox
import tornadofx.opcr

class HBoxWithItem<T>(val item: T, spacing: Double = 0.0, vararg children: Node?) : HBox(spacing, *children)

fun <T> EventTarget.hboxWithItem(
    item: T,
    spacing: Number? = null,
    alignment: Pos? = null,
    op: HBoxWithItem<T>.() -> Unit = {}
): HBoxWithItem<T> {
    val hbox = HBoxWithItem(item)
    if (alignment != null) hbox.alignment = alignment
    if (spacing != null) hbox.spacing = spacing.toDouble()
    return opcr(this, hbox, op)
}