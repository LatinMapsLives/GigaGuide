package ru.vsu.cs.iachnyi_m_a.gigaguide.mobile


import org.junit.Test
import ru.vsu.cs.iachnyi_m_a.gigaguide.mobile.validator.EmailValidator

class EmailValidatorTest {
    @Test
    fun validSimpleEmail(){
        assert(EmailValidator().validate("markyachny@gmail.com"))
        assert(EmailValidator().validate("mark132yachny430@gmail.com"))
    }

    @Test
    fun validComplexEmail(){
        assert(EmailValidator().validate("markyachny@voz.edu.vsu.ru"))
        assert(EmailValidator().validate("3marfakyach3ny@voz.edu.vsu.ru"))
    }

    @Test
    fun invalidEmailNoMailService(){
        assert(!EmailValidator().validate("markini@"))
        assert(!EmailValidator().validate("markini"))
    }

    @Test fun invalidEmailNoName(){
        assert(!EmailValidator().validate("@gmail.com"))
        assert(!EmailValidator().validate("gmail.com"))
    }

    @Test fun invalidEmailIllegalCharacters(){
        assert(!EmailValidator().validate("markячный@gmail.com"))
        assert(!EmailValidator().validate("markyachny@почта.com"))
    }

}