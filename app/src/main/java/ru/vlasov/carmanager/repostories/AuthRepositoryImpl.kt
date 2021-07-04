package ru.vlasov.carmanager.repostories

import retrofit2.HttpException
import ru.vlasov.carmanager.features.auth.AuthState
import ru.vlasov.carmanager.network.RemoteDataSource
import ru.vlasov.carmanager.network.json.request.LoginEntity
import ru.vlasov.carmanager.network.json.request.SignUpEntity
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor
    (val remoteDataSource: RemoteDataSource) : AuthRepository {

    override suspend fun login(username: String, password: String): AuthState {
        return try {
            val res = remoteDataSource.login(LoginEntity(username, password))
            AuthState.SuccessLogin(res)
        } catch(e : Exception) {
            when(e){
                is HttpException -> AuthState.Fail.UserNotFound()

                is IOException -> AuthState.Fail.NetworkError()

                else -> AuthState.Fail.UnexpectedState()
            }
        }
    }

    override suspend fun signUp(
        username: String,
        password: String,
        email: String
    ): AuthState {
        return try {
            val res = remoteDataSource.signUp(SignUpEntity(username, password, email))
            AuthState.SuccessSignUp(res)
        }catch(e : Exception) {
            when(e){
                is IOException -> AuthState.Fail.NetworkError()
                is HttpException -> {
                    val message = e.response()?.errorBody()?.string()
                    val mesList = message?.split(" ")
                    if(mesList == null)
                        AuthState.Fail.SignUp.WrongInput()
                    if(mesList?.isEmpty() != false)
                        AuthState.Fail.SignUp.WrongInput()
                    else{
                        if(mesList[1].toLowerCase() == "user")
                            AuthState.Fail.SignUp.UsernameAlreadyExist()
                        else if(mesList[1].toLowerCase() == "email")
                            AuthState.Fail.SignUp.EmailOccupied()
                        else AuthState.Fail.SignUp.WrongInput()
                    }
                }
                else -> AuthState.Fail.UnexpectedState()
            }
        }
    }


}