package client.view

import javafx.scene.Node
import tornadofx.View

abstract class ViewWithParams(title: String? = null, icon: Node? = null) : View(title, icon) {
    val viewParams = mutableMapOf<String, Any?>()

    fun <T> getParam(key: String, default: T? = null) = viewParams[key] as? T ?: default
}