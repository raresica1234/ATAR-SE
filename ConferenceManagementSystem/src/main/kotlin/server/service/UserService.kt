package server.service

import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.any
import org.ktorm.entity.find
import org.ktorm.entity.update
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

        fun login(email: String, password: String): User {
            val user = database.users.find { it.email eq email }
                ?: throw ValidationException(
                    "User does not exist!",
                    "The email provided is not associated with any user, try creating an account first."
                )

            if (user.password != password) {
                throw ValidationException(
                    "Password incorrect!",
                    "The given password does not match, please try again."
                )
            }

            return user
        }

        fun update(user: User): User {
            database.users.update(user);

            return user
        }
    }
}