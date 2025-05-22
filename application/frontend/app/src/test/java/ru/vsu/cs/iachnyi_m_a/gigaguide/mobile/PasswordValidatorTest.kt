package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile

import org.junit.Test
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator.PasswordValidator

class PasswordValidatorTest {

    @Test
    fun validPassword() {
        assert(PasswordValidator().validate("PasDs141i14_fa"))
    }

    @Test
    fun validPasswordShortest() {
        assert(PasswordValidator().validate("gk79fr6D"))
    }

    @Test
    fun validPasswordLongest() {
        assert(PasswordValidator().validate("diod6d9dnd7GHd9fj9ff"))
    }

    @Test
    fun invalidPasswordIllegalCharacters() {
        assert(!PasswordValidator().validate("PasDs1ф41i14_fa"))
    }

    @Test
    fun invalidPasswordNoUppercase() {
        assert(!PasswordValidator().validate("aasfs141i14_fa"))
    }

    @Test
    fun invalidPasswordNoLowercase() {
        assert(!PasswordValidator().validate("JFJSJN141FD14FS"))
    }

    @Test
    fun invalidPasswordNoDigits() {
        assert(!PasswordValidator().validate("uhhUgUGdDSdS"))
    }

    @Test
    fun invalidPasswordTooShort() {
        assert(!PasswordValidator().validate("djkD45d"))
    }

    @Test
    fun invalidPasswordTooLong() {
        assert(!PasswordValidator().validate("diod6d9dnd7GHd9fj95ff"))
    }
}