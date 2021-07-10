package ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel

sealed class CarCreatingState {

    object Success : CarCreatingState()

    class Error{
        object NetworkError : CarCreatingState()

        object RequestError : CarCreatingState()
    }

    object Loading : CarCreatingState()
}