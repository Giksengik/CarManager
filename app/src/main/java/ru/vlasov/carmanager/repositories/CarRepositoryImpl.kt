package ru.vlasov.carmanager.repositories

import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.network.json.main.RemoteDataSource
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : CarRepository {
    override suspend fun addCar(car: Car) {
        try {
            remoteDataSource.addCar(car)
        }
        catch(e : Exception){
            e.printStackTrace()
        }
    }

    override suspend fun updateCar(car: Car) {
        try{
            remoteDataSource.updateCar(car)
        }
        catch (e : java.lang.Exception){
            e.printStackTrace()
        }
    }

    override suspend fun getUserCars(): List<Car> {
        return try {
            remoteDataSource.getUserCars()
        }catch (e : java.lang.Exception){
            e.printStackTrace()
            listOf()
        }
    }


}