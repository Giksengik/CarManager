package ru.vlasov.carmanager.network.json.auth

import retrofit2.http.Body
import retrofit2.http.POST
import ru.vlasov.carmanager.network.json.auth.request.LoginEntity
import ru.vlasov.carmanager.network.json.auth.request.SignUpEntity
import ru.vlasov.carmanager.network.json.auth.response.LoginResponse
import ru.vlasov.carmanager.network.json.auth.response.MessageResponse

interface JsonCarManagerAuthApi {

    @POST("/api/auth/signin")
    suspend fun login(@Body loginEntity: LoginEntity) : LoginResponse

    @POST("/api/auth/signup")
    suspend fun signUp(@Body signUpEntity: SignUpEntity) : MessageResponse

}