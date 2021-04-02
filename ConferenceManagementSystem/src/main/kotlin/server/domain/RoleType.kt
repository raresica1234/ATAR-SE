package server.domain

enum class RoleType(val value: Int) {
    AUTHOR(1 shl 0),
    PROGRAM_COMMITTEE(1 shl 1),
    CHAIR(1 shl 2),
    CO_CHAIR(1 shl 3),
    AUTHORIZED_USER(1 shl 4)
}