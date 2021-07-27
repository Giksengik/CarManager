package ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.repositories.CarRepository
import javax.inject.Inject

@HiltViewModel
class CarsViewModelImpl @Inject constructor(private val carRepository: CarRepository) : ViewModel() {

    private val _userCars : MutableLiveData<CarDataRepresentationState> = MutableLiveData()
    val userCars : LiveData<CarDataRepresentationState>
    get() = _userCars

    fun getUserCars(){
        _userCars.value = CarDataRepresentationState.Loading
        viewModelScope.launch(Dispatchers.Default){
            _userCars.postValue(carRepository.getUserCars())
        }
    }
}