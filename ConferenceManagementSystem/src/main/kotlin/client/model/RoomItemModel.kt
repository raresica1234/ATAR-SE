package client.model

data class RoomItemModel(
    val id: Int = 0,
    val seats: Int = 0
) {
    override fun toString() = "Room with $seats seats"
}