package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.toList
import server.database
import server.roles

class RoleService {
    companion object {
        fun getUserRoles(userId: Int) = database.roles
            .filter { it.userId.eq(userId) }
            .toList()
    }
}