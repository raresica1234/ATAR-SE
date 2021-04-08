package client.view

import javafx.scene.Node
import tornadofx.View

abstract class ViewWithParams(title: String? = null, icon: Node? = null) : View(title, icon) {
    private val viewParams = mutableMapOf<String, Any?>()

    fun <T> getParam(key: String) = viewParams[key] as? T

    fun setParams(vararg params: Pair<String, Any?>) {
        viewParams.putAll(params)
        onParamSet()
    }

    abstract fun onParamSet()
}