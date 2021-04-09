package client.controller.conference

import client.model.conference.ConferenceListItemModel
import client.model.conference.ConferenceListModel
import server.domain.Conference
import tornadofx.Controller
import tornadofx.onChange
import java.time.LocalDate

class ConferenceListController : Controller() {
    val conferenceListModel = ConferenceListModel()

    init {
        conferenceListModel.search.onChange {
            val activeConferences = conferenceListModel.getInitialActiveConferences()
            val participatingConferences = conferenceListModel.getInitialParticipatingConferences()

            val searchValue = it?.trim().orEmpty()

            conferenceListModel.activeConferences.setAll(applySearch(activeConferences, searchValue))
            conferenceListModel.participatingConferences.setAll(applySearch(participatingConferences, searchValue))
        }
    }

    fun handleOnUndock() {
        conferenceListModel.allConferences.addAll(
            ConferenceListItemModel(Conference {
                name = "Conference 1"
                abstractDeadline = LocalDate.of(2021, 4, 1)
                paperDeadline = LocalDate.of(2021, 4, 10)
                biddingDeadline = LocalDate.of(2021, 4, 20)
                reviewDeadline = LocalDate.of(2021, 4, 30)
            }, "Biology, Computer Science", "None"),
            ConferenceListItemModel(Conference {
                name = "Conference 2"
                abstractDeadline = LocalDate.of(2021, 4, 1)
                paperDeadline = LocalDate.of(2021, 4, 10)
                biddingDeadline = LocalDate.of(2021, 4, 20)
                reviewDeadline = LocalDate.of(2021, 4, 30)
            }, "Biology, Computer Science", "None"),
            ConferenceListItemModel(Conference {
                name = "Conference 3"
                abstractDeadline = LocalDate.of(2021, 4, 1)
                paperDeadline = LocalDate.of(2021, 4, 10)
                biddingDeadline = LocalDate.of(2021, 4, 20)
                reviewDeadline = LocalDate.of(2021, 4, 30)
            }, "Biology, Computer Science", "None"),
            ConferenceListItemModel(Conference {
                name = "Conference 4"
                abstractDeadline = LocalDate.of(2021, 4, 1)
                paperDeadline = LocalDate.of(2021, 4, 10)
                biddingDeadline = LocalDate.of(2021, 4, 20)
                reviewDeadline = LocalDate.of(2021, 4, 30)
            }, "Biology, Computer Science", "None"),
            ConferenceListItemModel(Conference {
                name = "Conference 5"
                abstractDeadline = LocalDate.of(2021, 4, 1)
                paperDeadline = LocalDate.of(2021, 4, 10)
                biddingDeadline = LocalDate.of(2021, 4, 20)
                reviewDeadline = LocalDate.of(2021, 4, 30)
            }, "Biology, Computer Science", "None"),
            ConferenceListItemModel(Conference {
                name = "Conference 6"
                abstractDeadline = LocalDate.of(2021, 4, 1)
                paperDeadline = LocalDate.of(2021, 4, 10)
                biddingDeadline = LocalDate.of(2021, 4, 20)
                reviewDeadline = LocalDate.of(2021, 4, 30)
            }, "Biology, Computer Science", "None"),
            ConferenceListItemModel(Conference {
                name = "Conference 7"
                abstractDeadline = LocalDate.of(2021, 4, 1)
                paperDeadline = LocalDate.of(2021, 4, 10)
                biddingDeadline = LocalDate.of(2021, 4, 20)
                reviewDeadline = LocalDate.of(2021, 4, 30)
            }, "Biology, Computer Science", "None")
        )

        conferenceListModel.activeConferences.addAll(conferenceListModel.allConferences.take(5))
        conferenceListModel.participatingConferences.addAll(conferenceListModel.allConferences.takeLast(2))
    }

    private fun applySearch(conferences: List<ConferenceListItemModel>, searchValue: String) =
        conferences.filter { it.conference.name.contains(searchValue, true) }
}