package client.model

data class UserItemModel(var id: Int, var fullName: String, var email: String) {
    override fun toString() = "$fullName - $email"
}