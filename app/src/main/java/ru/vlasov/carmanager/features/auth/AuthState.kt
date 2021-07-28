package ru.vlasov.carmanager.features.auth

import ru.vlasov.carmanager.network.json.auth.response.LoginResponse
import ru.vlasov.carmanager.network.json.auth.response.MessageResponse

sealed class AuthState{

    class SuccessLogin(val loginResponse: LoginResponse) : AuthState()

    class SuccessSignUp(val messageResponse: MessageResponse) : AuthState()

    class Fail{
        object IncorrectPassword : AuthState()

        object IncorrectUsername : AuthState()

        object IncorrectEmail : AuthState()

        object NetworkError : AuthState()

        object UserNotFound : AuthState()

        object UnexpectedState : AuthState()

        class SignUp{
            object UsernameAlreadyExist : AuthState()

            object EmailOccupied : AuthState()

            object WrongInput : AuthState()
        }
    }

    object Loading : AuthState()

}
