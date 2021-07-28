package ru.vlasov.carmanager.repositories

import ru.vlasov.carmanager.features.bottomnav.cars.viewmodel.CarCreatingState
import ru.vlasov.carmanager.features.bottomnav.cars.viewmodel.CarDataRepresentationState
import ru.vlasov.carmanager.models.Car

interface CarRepository {
    suspend fun addCar(car : Car): CarCreatingState
    suspend fun updateCar(car : Car)
    suspend fun getUserCars() : CarDataRepresentationState
}
