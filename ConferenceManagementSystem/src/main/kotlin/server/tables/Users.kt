package server.tables

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import server.domain.User

object Users : Table<User>("users") {
    val id = int("id").primaryKey().bindTo { it.id }
    var email = varchar("email").bindTo { it.email }
    var password = varchar("password").bindTo { it.password }
    var firstName = varchar("firstname").bindTo { it.firstName }
    var lastName = varchar("lastname").bindTo { it.lastName }
    var affiliation = varchar("affiliation").bindTo { it.affiliation }
    var webpageLink = varchar("webpagelink").bindTo { it.webpageLink }
    var isSiteAdministrator = boolean("issiteadministrator").bindTo { it.isSiteAdministrator }
}