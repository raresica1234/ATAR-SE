package client.view

import javafx.scene.Node
import tornadofx.View

abstract class ViewWithParams(title: String? = null, icon: Node? = null) : View(title, icon) {
    val viewParams = mutableMapOf<String, Any?>()

    fun <T> getParam(key: String) = viewParams[key] as? T

    fun setParams(vararg params: Pair<String, Any?>) {
        viewParams.putAll(params)
        onParamSet(params.toMap())
    }

    abstract fun onParamSet(params: Map<String, Any?>)
}