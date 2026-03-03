package com.schoolbus.common.models

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val role: UserRole = UserRole.PARENT,
    val phoneNumber: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

enum class UserRole {
    ADMIN,
    DRIVER,
    PARENT
}
