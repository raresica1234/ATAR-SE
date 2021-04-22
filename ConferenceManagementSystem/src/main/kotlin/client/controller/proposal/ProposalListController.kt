package client.controller.proposal

import client.model.proposal.ProposalListItemModel
import client.model.proposal.ProposalListModel
import client.state.userState
import server.domain.Conference
import server.domain.RoleType
import server.service.ConferenceService
import server.service.RoleService
import tornadofx.Controller

class ProposalListController : Controller() {
    val model = ProposalListModel()

    fun onParamsSet(conferenceId: Int) {
        fetchData(conferenceId)
    }

    private fun fetchData(conferenceId: Int) {
        data class FetchData(
            val conference: Conference,
            val roleType: RoleType,
            val leftProposals: List<ProposalListItemModel>,
            val rightProposals: List<ProposalListItemModel>
        )
        runAsync {
            val conference = ConferenceService.get(conferenceId) ?: return@runAsync

            val role = RoleService.get(userState.user.id, conferenceId) ?: return@runAsync

            val leftProposals = if (role.roleType == RoleType.CHAIR) {
                emptyList<ProposalListItemModel>()
            } else {
                emptyList()
            }

            val rightProposals = if (role.roleType == RoleType.CHAIR) {
                emptyList<ProposalListItemModel>()
            } else {
                emptyList()
            }

            FetchData(conference, role.roleType, leftProposals, rightProposals)
        }
    }
}