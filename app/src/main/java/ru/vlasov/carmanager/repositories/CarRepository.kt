package ru.vlasov.carmanager.repositories

import ru.vlasov.carmanager.models.Car

interface CarRepository {
    suspend fun addCar(car : Car)
    suspend fun updateCar(car : Car)
    suspend fun getUserCars() : List<Car>
}