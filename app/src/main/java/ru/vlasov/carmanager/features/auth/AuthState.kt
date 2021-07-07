package ru.vlasov.carmanager.features.auth

import ru.vlasov.carmanager.network.json.auth.response.LoginResponse
import ru.vlasov.carmanager.network.json.auth.response.MessageResponse

sealed class AuthState{

    class SuccessLogin(val loginResponse: LoginResponse) : AuthState()

    class SuccessSignUp(val messageResponse: MessageResponse) : AuthState()

    class Fail(){
        class IncorrectPassword(): AuthState()

        class IncorrectUsername(): AuthState()

        class IncorrectEmail(): AuthState()

        class NetworkError(): AuthState()

        class UserNotFound(): AuthState()

        class UnexpectedState() : AuthState()

        class SignUp{
            class UsernameAlreadyExist() : AuthState()

            class EmailOccupied() : AuthState()

            class WrongInput() : AuthState()
        }
    }
    class Loading() : AuthState()

}
