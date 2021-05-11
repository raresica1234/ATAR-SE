package server.domain

enum class ApprovalStatus(val value: String) {
    TO_BE_REVIEWED("To be reviewed"),
    IN_REVIEW("In review"),
    IN_DISCUSSION("In discussion"),
    IN_CONFLICT("In conflict"),
    APPROVED("Approved"),
    REJECTED("Rejected")
}