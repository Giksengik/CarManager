package ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel

import ru.vlasov.carmanager.models.Car

sealed class CarDataRepresentationState{

    data class CarsLoaded(val cars :List<Car>) : CarDataRepresentationState()

    class Error{
        object NetworkError : CarDataRepresentationState()

        object RequestError : CarDataRepresentationState()
    }

    object Loading : CarDataRepresentationState()
}
