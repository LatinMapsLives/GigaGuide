package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator

class UsernameValidator {
    fun validate(username: String): Boolean{
        return Regex("[\\w\\s]{6,20}").matches(username)
    }
}