package tawuniya.challenge.domain.entity

data class UserData(
    val id: Long,
    val name: String?,
    val username: String?,
    val company: String?,
    val email: String?,
    val phone: String?,
    val website: String?,
    val address: String?
)
