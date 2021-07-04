package ru.vlasov.carmanager.network.json

import ru.vlasov.carmanager.network.RemoteDataSource
import ru.vlasov.carmanager.network.json.request.LoginEntity
import ru.vlasov.carmanager.network.json.request.SignUpEntity
import ru.vlasov.carmanager.network.json.response.LoginResponse
import ru.vlasov.carmanager.network.json.response.MessageResponse
import javax.inject.Inject


class DataSource @Inject constructor(val api : JsonCarManagerApi) : RemoteDataSource {

    override suspend fun login(loginEntity: LoginEntity): LoginResponse =
        api.login(loginEntity)

    override suspend fun signUp(signUpEntity: SignUpEntity): MessageResponse =
        api.signUp(signUpEntity)

}