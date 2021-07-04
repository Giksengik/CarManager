package ru.vlasov.carmanager.utils

import org.apache.commons.validator.routines.EmailValidator
import java.util.regex.Pattern

object InputDataValidator {

    fun checkUsername(username: String?) : Boolean {
        val usernameRegex = "^[A-Za-z]\\w{5,29}$"
        val usernamePattern = Pattern.compile(usernameRegex)
        if (username == null) {
            return false;
        }
        val matcher = usernamePattern.matcher(username)
        return matcher.matches()
    }

    fun checkPassword(password: String?) : Boolean {
        if(password == null)
            return false
        if (password.length < 8) {
            return false
        } else {
            var c : Char?
            var count = 0;
            for (element in password) {
                c = element
                if (!Character.isLetterOrDigit(c)) {
                    return false
                }
            }
        }
        return true
    }

    fun checkEmail(email: String?) : Boolean{
        val validator: EmailValidator = EmailValidator.getInstance()
        return validator.isValid(email)
    }

}
