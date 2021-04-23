package client.controller.proposal

import client.model.proposal.ProposalListItemModel
import client.model.proposal.ProposalListModel
import client.state.userState
import server.domain.Bid
import server.domain.BidType
import server.domain.Conference
import server.domain.RoleType
import server.service.BidService
import server.service.ProposalService
import server.service.ProposalWithAuthors
import server.service.RoleService
import tornadofx.Controller
import utils.hasPassed

class ProposalListController : Controller() {
    val model = ProposalListModel()

    fun onParamsSet(conference: Conference) {
        fetchData(conference)
    }

    private fun fetchData(conference: Conference) {
        data class FetchData(
            val roleType: RoleType,
            val leftProposals: List<ProposalListItemModel>,
            val rightProposals: List<ProposalListItemModel>
        )
        model.isLoading.set(true)
        runAsync {
            val userId = userState.user.id

            val role = RoleService.get(userId, conference.id)?.roleType ?: return@runAsync null

            val leftProposals = fetchLeftProposals(role, conference, userId)
                .map { ProposalListItemModel.from(it) }

            val rightProposals = fetchRightProposals(role, conference, userId)
                .map { ProposalListItemModel.from(it) }

            FetchData(role, leftProposals, rightProposals)
        } ui {
            it?.apply {
                model.conference.set(conference)
                model.role.set(roleType)
                model.leftTabProposals.setAll(leftProposals)
                model.rightTabProposals.setAll(rightProposals)
            }
            model.isLoading.set(false)
        }
    }

    private fun fetchLeftProposals(roleType: RoleType, conference: Conference, userId: Int): List<ProposalWithAuthors> {
        if (roleType == RoleType.CHAIR) {
            return ProposalService.getAllWithAuthorsByConference(conference.id)
        }
        if (conference.biddingDeadline?.hasPassed() == true) {
            return emptyList()
        }
        return ProposalService.getAllForBiddingWithAuthors(conference.id, userId)
    }

    private fun fetchRightProposals(
        roleType: RoleType,
        conference: Conference,
        userId: Int
    ): List<ProposalWithAuthors> {
        if (conference.reviewDeadline?.hasPassed() == true) {
            return emptyList()
        }
        if (roleType == RoleType.CHAIR) {
            return ProposalService.getAllInConflictWithAuthorsByConference(conference.id)
        }
        return ProposalService.getAllForReviewWithAuthors(conference.id, userId)
    }

    fun handleBids(proposalId: Int, pcMemberId: Int, bidType: BidType): Bid? {
        val bid =  BidService.add(proposalId, pcMemberId, bidType)
        model.leftTabProposals.removeIf { it.id == proposalId }

        return bid
    }
}