package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import server.domain.Role
import server.domain.RoleType

object Roles : Table<Role>("roles") {
    var userId = int("userid").primaryKey().bindTo { it.userId }
    var conferenceId = int("conferenceid").primaryKey().bindTo { it.conferenceId }
    var roleType = enum<RoleType>("roletype").bindTo { it.roleType }
}