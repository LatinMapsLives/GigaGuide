package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator

class PasswordValidator {
    fun validate(password: String): Boolean{
        return Regex("\\w{8,20}").matches(password) && Regex("\\d").containsMatchIn(password) && Regex("[a-z]").containsMatchIn(password) && Regex("[A-Z]").containsMatchIn(password)
    }
}