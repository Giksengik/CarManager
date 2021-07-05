package ru.vlasov.carmanager.features.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.features.auth.AuthState
import ru.vlasov.carmanager.repositories.AuthRepository
import ru.vlasov.carmanager.utils.InputDataValidator
import javax.inject.Inject

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(val repository: AuthRepository) : ViewModel() {
    private val mutableViewState : MutableLiveData<List<AuthState>> =
        MutableLiveData()
    val viewState : LiveData<List<AuthState>>
        get() = mutableViewState

    fun trySignUp(username: String?, password: String?, email : String?){
        mutableViewState.value = listOf(AuthState.Loading())
        val stateList : MutableList<AuthState> = mutableListOf()
        if(!InputDataValidator.checkUsername(username))
            stateList.add(AuthState.Fail.IncorrectUsername())
        if(!InputDataValidator.checkPassword(password))
            stateList.add(AuthState.Fail.IncorrectPassword())
        if(!InputDataValidator.checkEmail(email))
            stateList.add(AuthState.Fail.IncorrectEmail())
        if(stateList.size == 0){
            viewModelScope.launch(Dispatchers.Default){
                val resState = repository.signUp(username ?: "", password ?: "", email ?: "")
                mutableViewState.postValue(listOf(resState))
            }
        }else{
            mutableViewState.value = stateList
        }

    }

}