package ru.vlasov.carmanager.network.json.auth

import ru.vlasov.carmanager.di.network.AuthApi
import ru.vlasov.carmanager.network.json.auth.request.LoginEntity
import ru.vlasov.carmanager.network.json.auth.request.SignUpEntity
import ru.vlasov.carmanager.network.json.auth.response.LoginResponse
import ru.vlasov.carmanager.network.json.auth.response.MessageResponse
import javax.inject.Inject


class AuthDataSource @Inject constructor(@AuthApi val api : JsonCarManagerAuthApi) : RemoteAuthDataSource {

    override suspend fun login(loginEntity: LoginEntity): LoginResponse =
        api.login(loginEntity)

    override suspend fun signUp(signUpEntity: SignUpEntity): MessageResponse =
        api.signUp(signUpEntity)

}