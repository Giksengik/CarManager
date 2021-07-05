package ru.vlasov.carmanager.repositories

import android.preference.PreferenceManager.getDefaultSharedPreferences
import retrofit2.HttpException
import ru.vlasov.carmanager.data.UserDataHolder
import ru.vlasov.carmanager.features.auth.AuthState
import ru.vlasov.carmanager.network.RemoteDataSource
import ru.vlasov.carmanager.network.json.request.LoginEntity
import ru.vlasov.carmanager.network.json.request.SignUpEntity
import java.io.IOException
import java.lang.IllegalArgumentException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor
    (val remoteDataSource: RemoteDataSource, val userDataHolder: UserDataHolder) : AuthRepository {

    override suspend fun login(username: String, password: String): AuthState {
        return try {
            val res = remoteDataSource.login(LoginEntity(username, password))
            userDataHolder.dataHolder.edit().apply{
                putLong(UserDataHolder.ID_KEY, res.id ?: throw IllegalArgumentException("id not found"))
                putString(UserDataHolder.TOKEN_KEY, res.token ?: throw IllegalArgumentException("token not found"))
                putString(UserDataHolder.EMAIL_KEY, res.email ?: throw IllegalArgumentException("email not found"))
                putString(UserDataHolder.TYPE_KEY, res.type ?: throw IllegalArgumentException("token type not found"))
                putString(UserDataHolder.USERNAME_KEY, res.username ?: throw IllegalArgumentException("username not found"))
            }
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