package client.model

import javafx.beans.property.SimpleObjectProperty

class ConflictItemModel(
    val proposal: SimpleObjectProperty<DetailedProposalItemModel> = SimpleObjectProperty()
)