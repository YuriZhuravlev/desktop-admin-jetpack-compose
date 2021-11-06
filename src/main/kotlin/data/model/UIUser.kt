package data.model

data class UIUser(
    val id: Int,
    val username: String,
    val password: ByteArray,
    val isBlocked: Boolean,
    val strongPassword: Boolean
)

const val SUPERUSER_NAME = "ADMIN"