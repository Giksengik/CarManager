package ru.vlasov.carmanager.repositories

import ru.vlasov.carmanager.features.auth.AuthState


interface AuthRepository {
    suspend fun login(username : String, password: String) : AuthState
    suspend fun signUp(username : String, password: String, email : String) : AuthState
}