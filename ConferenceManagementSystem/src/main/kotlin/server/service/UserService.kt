package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.any
import org.ktorm.entity.find
import server.database
import server.domain.User
import server.users
import utils.ValidationException
import utils.isEmail

class UserService {
    companion object {
        fun createAccount(user: User) {
            if (!user.email.isEmail()) {
                throw ValidationException(
                    "Email is invalid!",
                    "The provided email address is not valid, verify it and please try again."
                )
            }
            if (database.users.any { it.email.eq(user.email) }) {
                throw ValidationException(
                    "Email is in use!",
                    "The provided email address is already in use. Try to log in or choose a different email address."
                )
            }
            database.users.add(user)
        }

        fun getAccountFromEmail(email: String) : User? {
            return database.users.find { it.email eq email }
        }
    }
}