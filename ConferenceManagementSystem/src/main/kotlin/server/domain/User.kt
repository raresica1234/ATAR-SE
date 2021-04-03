package server.domain

data class User(
    val id: Int,
    var email: String,
    var password: String = "",
    var firstName: String = "",
    var affiliation: String = "",
    var webpageLink: String = "",
    var isSiteAdministrator: Boolean = false
) : BaseEntity<Int>(id)
