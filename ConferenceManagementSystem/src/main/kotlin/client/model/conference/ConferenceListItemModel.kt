package client.model.conference

import server.domain.Conference

data class ConferenceListItemModel(
    val id: Int,
    val conference: Conference,
    val sections: String,
    val papers: String,
    val hideExtraButton: Boolean
) {
    override fun toString() = conference.name
}