package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import org.junit.Test
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator.UsernameValidator

class UsernameValidatorTest {
    @Test
    fun validUsername() {
        assert(UsernameValidator().validate("15Ячный Mark34"))
    }

    @Test
    fun validUsernameShortest() {
        assert(UsernameValidator().validate("Husr67"))
    }

    @Test
    fun validUsernameLongest() {
        assert(UsernameValidator().validate("Husr67_Марк2005d6xxx"))
    }

    @Test
    fun validUsernameTooShort() {
        assert(!UsernameValidator().validate("Usr67"))
    }

    @Test
    fun validUsernameTooLong() {
        assert(!UsernameValidator().validate("Husr67_Мfарк2005d6xxx"))
    }

    @Test
    fun validUsernameIllegalCharacters() {
        assert(!UsernameValidator().validate("Марк :))) в56"))
    }
}