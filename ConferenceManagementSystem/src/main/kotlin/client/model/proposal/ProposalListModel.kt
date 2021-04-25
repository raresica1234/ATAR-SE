package client.model.proposal

import client.model.DetailedProposalItemModel
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import server.domain.Conference
import server.domain.RoleType
import tornadofx.observableListOf

data class ProposalListModel(
    val conference: SimpleObjectProperty<Conference> = SimpleObjectProperty(),
    val role: SimpleObjectProperty<RoleType> = SimpleObjectProperty(),
    val isLoading: SimpleBooleanProperty = SimpleBooleanProperty(true),
    val selectedProposal: SimpleObjectProperty<DetailedProposalItemModel> = SimpleObjectProperty(),
    val leftTabProposals: ObservableList<DetailedProposalItemModel> = observableListOf(),
    val rightTabProposals: ObservableList<DetailedProposalItemModel> = observableListOf(),
)