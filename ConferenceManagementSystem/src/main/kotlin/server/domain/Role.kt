package server.domain

import org.ktorm.entity.Entity

interface Role : Entity<Role> {
    companion object : Entity.Factory<Role>()

    var userId: Int
    var conferenceId: Int
    var roleType: RoleType
}