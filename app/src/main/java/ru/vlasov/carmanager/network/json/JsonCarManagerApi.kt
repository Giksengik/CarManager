package ru.vlasov.carmanager.network.json

import retrofit2.http.Body
import retrofit2.http.POST
import ru.vlasov.carmanager.network.json.request.LoginEntity
import ru.vlasov.carmanager.network.json.request.SignUpEntity
import ru.vlasov.carmanager.network.json.response.LoginResponse
import ru.vlasov.carmanager.network.json.response.MessageResponse

interface JsonCarManagerApi {

    @POST("/api/auth/signin")
    suspend fun login(@Body loginEntity: LoginEntity) : LoginResponse

    @POST("/api/auth/signup")
    suspend fun signUp(@Body signUpEntity: SignUpEntity) : MessageResponse

}