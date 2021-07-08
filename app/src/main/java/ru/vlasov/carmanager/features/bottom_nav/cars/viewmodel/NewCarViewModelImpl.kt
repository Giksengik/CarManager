package ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.repositories.CarRepository
import javax.inject.Inject

@HiltViewModel
class NewCarViewModelImpl @Inject constructor(private val repository: CarRepository) : ViewModel(){

    fun addNewCar(car : Car){
        viewModelScope.launch(Dispatchers.Default) {
            repository.addCar(car)
        }
    }
}