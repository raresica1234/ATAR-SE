package server.service

import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.notEq
import org.ktorm.entity.*
import server.database
import server.domain.Role
import server.domain.RoleType
import server.domain.User
import server.roles
import java.lang.Exception

class RoleService {
    companion object {
        fun getAllByUserId(userId: Int) = database.roles
            .filter { it.userId.eq(userId) }
            .toList()

        fun add(userId: Int, conferenceId: Int, roleType: RoleType): Role? {
            val role = Role {
                this.userId = userId
                this.conferenceId = conferenceId
                this.roleType = roleType
            }

            try {
                database.roles.add(role)
            } catch (exception: Exception) {
                println("Exception in RoleService.add($role): $exception")
                return null
            }

            return role
        }

        fun update(userId: Int, conferenceId: Int, roleType: RoleType): Role {
            val role = Role {
                this.conferenceId = conferenceId
                this.roleType = roleType
                this.userId = userId
            }

            database.roles.update(role)

            return role
        }

        fun get(userId: Int, conferenceId: Int) = database.roles.find {
            it.userId.eq(userId).and(it.conferenceId.eq(conferenceId))
        }

        fun delete(userId: Int, conferenceId: Int, roleType: RoleType? = null) = database.roles.removeIf {
            val mainCondition = it.userId.eq(userId).and(it.conferenceId.eq(conferenceId))

            if (roleType == null) {
                mainCondition
            } else {
                mainCondition.and(it.roleType.eq(roleType))
            }
        }
    }
}
