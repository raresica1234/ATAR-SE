package client.controller

import javafx.collections.FXCollections
import tornadofx.Controller
import client.model.CarModel

class UIController : Controller() {
    val values = FXCollections.observableArrayList(
        CarModel("Ford", "Focus"),
        CarModel("Volkswagen", "Golf"),
        CarModel("Skoda", "Octavia")
    )
}