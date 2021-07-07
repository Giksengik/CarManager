package ru.vlasov.carmanager.features.bottom_nav.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.repositories.CarRepository
import javax.inject.Inject

@HiltViewModel
class CarsViewModelImpl @Inject constructor(val carRepository: CarRepository) : ViewModel() {

    private val mutableUserCars : MutableLiveData<List<Car>> = MutableLiveData()
    val userCars : LiveData<List<Car>>
    get() = mutableUserCars

    fun getUserCars(){
        viewModelScope.launch(Dispatchers.Default){
            mutableUserCars.postValue(carRepository.getUserCars())
        }
    }
}