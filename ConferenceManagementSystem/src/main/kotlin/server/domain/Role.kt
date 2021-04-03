package server.domain

import org.ktorm.entity.Entity

interface Role : Entity<Role> {
    companion object : Entity.Factory<Role>()

    val user: User
    val conference: Conference
    var roleType: RoleType
}