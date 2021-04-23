package server.domain

enum class BidType(val value: String) {
    PLEASED_TO_REVIEW("Pleased to review"),
    COULD_REVIEW("Could review"),
    REFUSE_TO_REVIEW("Refuse to review"),
    IN_CONFLICT("In conflict")
}
