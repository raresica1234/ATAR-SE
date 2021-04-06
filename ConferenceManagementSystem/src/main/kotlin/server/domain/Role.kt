package server.domain

import org.ktorm.entity.Entity

interface Role : Entity<Role> {
    companion object : Entity.Factory<Role>()

    var user: User
    var conference: Conference
    var roleType: RoleType
}