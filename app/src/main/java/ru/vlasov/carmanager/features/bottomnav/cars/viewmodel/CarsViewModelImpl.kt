package ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.repositories.CarRepository
import javax.inject.Inject

@HiltViewModel
class CarsViewModelImpl @Inject constructor(private val carRepository: CarRepository) : ViewModel() {

    private val _userCars : MutableStateFlow<CarDataRepresentationState> =
        MutableStateFlow(CarDataRepresentationState.CarsLoaded(listOf()))
    val userCars : StateFlow<CarDataRepresentationState>
    get() = _userCars.asStateFlow()

    fun getUserCars(){
        _userCars.value = CarDataRepresentationState.Loading
        viewModelScope.launch(Dispatchers.Default){
            _userCars.value = carRepository.getUserCars()
        }
    }
}