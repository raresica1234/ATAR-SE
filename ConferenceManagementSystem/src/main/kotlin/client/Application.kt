package client

import client.view.account.LoginView
import client.view.conference.ConferenceListView
import tornadofx.App

/** Documentation at: https://edvin.gitbooks.io/tornadofx-guide/content/part1/3_Components.html */
class Application: App(ConferenceListView::class)