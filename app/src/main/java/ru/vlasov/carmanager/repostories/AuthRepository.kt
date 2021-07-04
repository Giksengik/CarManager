package ru.vlasov.carmanager.repostories

import ru.vlasov.carmanager.features.auth.AuthState
import ru.vlasov.carmanager.network.json.response.MessageResponse


interface AuthRepository {
    suspend fun login(username : String, password: String) : AuthState
    suspend fun signUp(username : String, password: String, email : String) : AuthState
}