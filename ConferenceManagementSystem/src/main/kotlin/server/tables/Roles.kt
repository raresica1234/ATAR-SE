package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.enum
import org.ktorm.schema.int
import server.domain.Role
import server.domain.RoleType

object Roles : Table<Role>("roles") {
    val userId = int("userid").primaryKey().references(Users) { it.user }
    val conferenceId = int("conferenceid").primaryKey().references(Conferences) { it.conference }
    var roleType = enum<RoleType>("roletype").bindTo { it.roleType }
}