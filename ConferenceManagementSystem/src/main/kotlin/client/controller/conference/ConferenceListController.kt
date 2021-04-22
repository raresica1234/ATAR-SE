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
                    // TODO: Maybe add more to the sections
                    val sectionsString = conferenceWithData.sections.joinToString { it.name }.ifEmpty { "None" }
                    val proposalsString = conferenceWithData.proposals.joinToString { it.name }.ifEmpty { "None" }

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
            }
        } ui {
            with(model) {
                activeConferences.setAll(initialActiveConferences)
                participatingConferences.setAll(initialParticipatingConferences)
                isLoading.set(false)
            }
        }
    }

    fun isListener(): Boolean {
        val conferenceId = model.getConferenceId()
        val userId = userState.user.id

        return model.roles.any {
            it.conferenceId == conferenceId && it.userId == userId && it.roleType == RoleType.LISTENER
        }
    }

    fun isAuthor(): Boolean {
        val conferenceId = model.getConferenceId()
        val userId = userState.user.id

        return model.roles.any {
            it.conferenceId == conferenceId && it.userId == userId && it.roleType == RoleType.AUTHOR
        }
    }

    private fun applySearch(conferences: List<ConferenceListItemModel>, searchValue: String) =
        conferences.filter { it.conference.name.contains(searchValue, true) }

    private fun hasPermissionToManage(role: Role) =
        userState.user.isSiteAdministrator || role.roleType == RoleType.CHAIR

    fun isAuthor(): Boolean {
        val conferenceId = model.getConferenceId()
        val userId = userState.user.id

        return model.roles.any {
            it.conferenceId == conferenceId && it.roleType == RoleType.AUTHOR && it.userId == userId
        }
    }
}