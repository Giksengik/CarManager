package ru.vlasov.carmanager.network.json.auth

import ru.vlasov.carmanager.network.json.auth.request.LoginEntity
import ru.vlasov.carmanager.network.json.auth.request.SignUpEntity
import ru.vlasov.carmanager.network.json.auth.response.LoginResponse
import ru.vlasov.carmanager.network.json.auth.response.MessageResponse

interface RemoteAuthDataSource {
    suspend fun login (loginEntity: LoginEntity) : LoginResponse

    suspend fun signUp (signUpEntity: SignUpEntity) : MessageResponse
}