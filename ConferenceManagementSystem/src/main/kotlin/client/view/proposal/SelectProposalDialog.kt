package client.view.proposal

import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import server.domain.Proposal
import tornadofx.*

data class ProposalDialogModel(val id: Int, val name: String)

fun UIComponent.selectProposalDialog(
    proposalsSource: () -> List<Proposal>,
    onSelect: (ProposalDialogModel?) -> Unit = {}
) = dialog {
    minWidth = 192.0
    minHeight = 64.0
    spacing = 16.0
    alignment = Pos.CENTER

    val selected = SimpleObjectProperty<ProposalDialogModel>()
    val proposals = observableListOf<ProposalDialogModel>()

    runAsync {
        proposalsSource().map { ProposalDialogModel(it.id, it.name) }
    } ui {
        proposals.setAll(it)
    }

    vbox {
        label("Select proposal:")
        combobox(selected, proposals)
    }

    hbox(16.0, Pos.CENTER_RIGHT) {
        button("Close") {
            action {
                close()
            }
        }
        button("Select") {
            action {
                onSelect(selected.get())
                close()
            }
        }
    }
}