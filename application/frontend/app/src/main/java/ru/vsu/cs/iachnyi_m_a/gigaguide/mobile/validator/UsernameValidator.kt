package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator

class UsernameValidator {
    fun validate(username: String): Boolean{
        return Regex("^[\\wа-яА-Я][\\wа-яА-Я\\s]{4,18}[\\wа-яА-Я]$").matches(username)
    }
}