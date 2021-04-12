package client.controller.conference

import client.model.conference.ConferenceListItemModel
import client.model.conference.ConferenceListModel
import client.state.userState
import server.domain.Role
import server.domain.RoleType
import server.service.ConferenceService
import server.service.RoleService
import tornadofx.Controller
import tornadofx.onChange
import utils.hasPassed
import utils.joinOrDefault

class ConferenceListController : Controller() {
    val model = ConferenceListModel()

    init {
        with(model) {
            search.onChange {
                val searchValue = it?.trim().orEmpty()

                activeConferences.setAll(applySearch(initialActiveConferences, searchValue))
                participatingConferences.setAll(applySearch(initialParticipatingConferences, searchValue))
            }
        }
    }

    fun refreshData() {
        model.clear()

        runAsync {
            val allConferences = ConferenceService.getAllActiveWithSectionsAndProposals()

            with(model) {
                roles.setAll(RoleService.getAllByUserId(userState.user.id))

                allConferences.forEach { conferenceWithData ->
                    val conference = conferenceWithData.conference
                    val sectionsString = conferenceWithData.sections.joinOrDefault(", ", "None")
                    val proposalsString = conferenceWithData.proposals.joinOrDefault(", ", "None")

                    val rolesForConference = roles.find { it.conferenceId == conference.id }

                    if (rolesForConference == null) {
                        initialActiveConferences.add(
                            ConferenceListItemModel(
                                conference.id,
                                conference,
                                sectionsString,
                                proposalsString,
                                conference.paperDeadline?.hasPassed() ?: true
                            )
                        )
                    } else {
                        initialParticipatingConferences.add(
                            ConferenceListItemModel(
                                conference.id,
                                conference,
                                sectionsString,
                                proposalsString,
                                !hasPermissionToManage(rolesForConference)
                            )
                        )
                    }
                }

                activeConferences.setAll(initialActiveConferences)
                participatingConferences.setAll(initialParticipatingConferences)
            }
        } ui {
            model.isLoading.set(false)
        }
    }

    private fun applySearch(conferences: List<ConferenceListItemModel>, searchValue: String) =
        conferences.filter { it.conference.name.contains(searchValue, true) }

    private fun hasPermissionToManage(role: Role) =
        userState.user.isSiteAdministrator || role.roleType == RoleType.CHAIR
}