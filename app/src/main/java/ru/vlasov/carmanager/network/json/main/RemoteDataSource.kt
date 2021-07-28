package ru.vlasov.carmanager.network.json.main

import ru.vlasov.carmanager.models.Car

interface RemoteDataSource {
    suspend fun addCar(car : Car)
    suspend fun updateCar(car : Car)
    suspend fun getUserCars() : List<Car>
}
