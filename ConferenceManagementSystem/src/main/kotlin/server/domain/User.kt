package server.domain

import org.ktorm.entity.Entity

interface User : Entity<User> {
    companion object : Entity.Factory<User>()

    val id: Int
    var email: String
    var password: String
    var firstName: String
    var lastName: String
    var affiliation: String
    var webpageLink: String
    var isSiteAdministrator: Boolean

    val fullName: String
        get() = "$firstName $lastName"
}