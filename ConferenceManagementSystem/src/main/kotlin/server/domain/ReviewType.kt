package server.domain

enum class ReviewType(val value: Int) {
    STRONG_REJECT(-3),
    REJECT(-2),
    WEAK_REJECT(-1),
    BORDERLINE_PAPER(0),
    WEAK_ACCEPT(1),
    ACCEPT(2),
    STRONG_ACCEPT(3)
}
