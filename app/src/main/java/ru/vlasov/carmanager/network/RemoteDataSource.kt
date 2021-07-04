package ru.vlasov.carmanager.network

import ru.vlasov.carmanager.network.json.request.LoginEntity
import ru.vlasov.carmanager.network.json.request.SignUpEntity
import ru.vlasov.carmanager.network.json.response.LoginResponse
import ru.vlasov.carmanager.network.json.response.MessageResponse

interface RemoteDataSource {
    suspend fun login (loginEntity: LoginEntity) : LoginResponse

    suspend fun signUp (signUpEntity: SignUpEntity) : MessageResponse
}