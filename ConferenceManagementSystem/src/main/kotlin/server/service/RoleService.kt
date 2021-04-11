package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import server.database
import server.domain.Role
import server.domain.RoleType
import server.roles

class RoleService {
    companion object {
        fun getAllByUserId(userId: Int) = database.roles
            .filter { it.userId.eq(userId) }
            .toList()

        fun add(userId: Int, conferenceId: Int, roleType: RoleType): Role {
            val role = Role {
                this.conferenceId = conferenceId
                this.roleType = roleType
                this.userId = userId
            }

            database.roles.add(role)

            return role
        }
    }
}
