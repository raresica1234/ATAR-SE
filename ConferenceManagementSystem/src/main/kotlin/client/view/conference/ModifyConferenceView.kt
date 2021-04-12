package client.view.conference

import client.controller.conference.ModifyConferenceController
import client.model.ProposalItemModel
import client.model.SelectedUserItemModel
import client.model.UserItemModel
import client.view.ViewWithParams
import client.view.component.datePicker
import client.view.component.setNode
import client.view.component.vBoxPane
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.SelectionMode
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.CheckBoxListCell
import javafx.scene.text.Font
import javafx.util.Callback
import tornadofx.*
import utils.APPLICATION_TITLE

class ModifyConferenceView : ViewWithParams(APPLICATION_TITLE) {
    private val controller by inject<ModifyConferenceController>()

    private val LEFT_SIDE_WIDTH = 160.0

    override fun onParamSet() {
        TODO("Not yet implemented")
    }

    override val root = vbox(alignment = Pos.CENTER) {
        paddingAll = 32.0

        vbox(32.0) {
            text("Modify Conference") {
                font = Font(24.0)
            }

            hbox(128.0) {
                vbox(16.0) {
                    vbox {
                        maxWidth = LEFT_SIDE_WIDTH

                        label("Name")
                        textfield() {
                            promptText = "Name"
                        }
                    }

                    vbox(8.0) {
                        text("Deadlines") {
                            font = Font(18.0)
                        }

                        datePicker("Abstract paper deadline", SimpleObjectProperty(), LEFT_SIDE_WIDTH)
                        datePicker("Full paper deadline", SimpleObjectProperty(), LEFT_SIDE_WIDTH)
                        datePicker("Bidding deadline", SimpleObjectProperty(), LEFT_SIDE_WIDTH)
                        datePicker("Review deadline", SimpleObjectProperty(), LEFT_SIDE_WIDTH)
                    }
                }
                vbox(16.0) {
                    vbox {
                        maxWidth = 256.0

                        label("Co-chair")
                        combobox<UserItemModel>() {
                            minWidth = 256.0
                            promptText = "Select a co-chair"
                        }
                    }
                    vbox {
                        maxWidth = 256.0

                        label("Program committees")
                        textfield() {
                            promptText = "Search"
                        }
                        listview<SelectedUserItemModel>(FXCollections.observableArrayList()) {
                            minWidth = 256.0
                            maxHeight = 182.0
                            cellFactory = CheckBoxListCell.forListView { it.selected }
                        }
                    }
                }
            }

            hbox(16.0) {
                maxHeight = 384.0

                vbox(4.0) {
                    hbox(LEFT_SIDE_WIDTH - 105.0) {
                        text("Sections") {
                            font = Font(18.0)
                        }

                        button("Add") { }
                    }

                    listview<String>(FXCollections.observableArrayList()) {
                        maxWidth = LEFT_SIDE_WIDTH
                    }
                }

                vBoxPane(32.0) {
                    hbox(32.0) {
                        vbox(8.0) {
                            vbox {
                                maxWidth = LEFT_SIDE_WIDTH

                                label("Name")
                                textfield() {
                                    promptText = "Name"
                                }
                            }
                            datePicker("Start date", SimpleObjectProperty(), LEFT_SIDE_WIDTH)
                        }
                        vbox(8.0) {
                            vbox {
                                maxWidth = LEFT_SIDE_WIDTH

                                label("Room")
                                combobox<String>() {
                                    minWidth = LEFT_SIDE_WIDTH
                                    promptText = "Select a room"
                                }
                            }
                            datePicker("End date", SimpleObjectProperty(), LEFT_SIDE_WIDTH)
                        }
                        button("Delete")
                    }

                    vbox(4.0) {
                        hbox(370.0, Pos.BOTTOM_LEFT) {
                            text("Proposals") {
                                font = Font(14.0)
                            }
                            button("Add")
                        }

                        tableview(FXCollections.observableArrayList(
                            ProposalItemModel(1, "Test", "Ana")
                        )) {
                            readonlyColumn("Proposal name", ProposalItemModel::name)
                            readonlyColumn("Proposal authors", ProposalItemModel::name).minWidth = 256.0
                            readonlyColumn("Actions", ProposalItemModel::id).setNode {

                                button("Remove") {
                                    action { println("Remove proposal $item") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}