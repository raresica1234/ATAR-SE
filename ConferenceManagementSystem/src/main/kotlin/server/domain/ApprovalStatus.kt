package server.domain

enum class ApprovalStatus(val value: String) {
    TO_BE_REVIEWED("To be reviewed"),
    IN_REVIEW("In review"),
    IN_CONFLICT("In conflict"),
    APPROVED("In conflict"),
    REJECTED("Rejected")
}