package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.dto

data class RegisterUserDTO(
    var username: String,
    var email: String,
    var password: String,
    var confirmPassword: String
)
