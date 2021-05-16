package server.domain

enum class ReviewType(val value: Int, val displayName: String) {
    STRONG_REJECT(-3, "Strong reject"),
    REJECT(-2, "Reject"),
    WEAK_REJECT(-1, "Weak reject"),
    BORDERLINE_PAPER(0, "Borderline paper"),
    WEAK_ACCEPT(1, "Weak accept"),
    ACCEPT(2, "Accept"),
    STRONG_ACCEPT(3, "Strong accept"),
    INVALID(0, "Invalid");

    override fun toString() = displayName
}
