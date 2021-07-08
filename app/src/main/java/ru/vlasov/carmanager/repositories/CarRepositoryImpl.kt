package ru.vlasov.carmanager.repositories

import retrofit2.HttpException
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.CarDataRepresentationState
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.network.json.main.RemoteDataSource
import java.io.IOException
import java.lang.IllegalArgumentException
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

    override suspend fun getUserCars(): CarDataRepresentationState {
        return try {
            val res = remoteDataSource.getUserCars()
            return CarDataRepresentationState.CarsLoaded(res)
        }catch (e : java.lang.Exception){
            when(e){
                is HttpException -> return CarDataRepresentationState.Error.RequestError

                is IOException -> CarDataRepresentationState.Error.NetworkError
                else -> {
                    e.printStackTrace()
                    throw IllegalArgumentException("unexpected state")
                }
            }
        }
    }


}