package ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.repositories.CarRepository
import javax.inject.Inject

@HiltViewModel
class  NewCarViewModelImpl @Inject constructor(private val repository: CarRepository) : ViewModel(){

    private val _viewState : MutableStateFlow<CarCreatingState> =
        MutableStateFlow(CarCreatingState.Waiting)
    val viewState : StateFlow<CarCreatingState>
    get() = _viewState

    fun addNewCar(car : Car){
        _viewState.value = CarCreatingState.Loading
        viewModelScope.launch(Dispatchers.Default) {
            val state = repository.addCar(car)
            _viewState.value =  state
        }
    }
}