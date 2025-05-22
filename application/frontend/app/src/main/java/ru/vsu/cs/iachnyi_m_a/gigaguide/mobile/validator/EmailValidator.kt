package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator

import android.util.Patterns

class EmailValidator {
    fun validate(email: String): Boolean{
        return Regex("[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+").matches(email)
    }
}