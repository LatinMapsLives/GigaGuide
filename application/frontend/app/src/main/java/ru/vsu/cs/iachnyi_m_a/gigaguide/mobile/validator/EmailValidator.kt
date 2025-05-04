package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator

import android.util.Patterns

class EmailValidator {
    fun validate(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}