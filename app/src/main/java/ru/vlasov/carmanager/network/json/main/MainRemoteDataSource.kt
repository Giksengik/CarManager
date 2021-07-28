package ru.vlasov.carmanager.network.json.main

import ru.vlasov.carmanager.data.UserDataHolder
import ru.vlasov.carmanager.di.network.MainApi
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.models.CarEngine
import ru.vlasov.carmanager.network.json.main.request.CarEngineRequest
import ru.vlasov.carmanager.network.json.main.request.CarRequest
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(
    @MainApi val api: JsonCarManagerApi, val userDataHolder: UserDataHolder) : RemoteDataSource {

    override suspend fun addCar(car : Car){
        val carToInsert = CarRequest(
                name = car.name,
                userId = userDataHolder.dataHolder.getLong(UserDataHolder.ID_KEY, -1),
                yearOfIssue = car.yearOfIssue,
                mileageInKm = car.mileageInKm,
                automobileBody = car.automobileBody,
                color = car.color,
                carEngine = CarEngineRequest(
                        cylinderVolumeInLitres = car.engine.cylinderVolumeInLitres,
                        enginePower = car.engine.enginePower,
                        type = car.engine.type
                ),
                taxPerYear = car.taxPerYear,
                transmissionType = car.transmissionType,
                typeOfDriverUnit = car.typeOfDriveUnit,
                steeringWheelLocation = car.steeringWheelLocation,
                vin = car.vin,
                stateNumber = car.stateNumber,
                description = car.description
        )
        api.addCar(carToInsert)
    }

    override suspend fun updateCar(car: Car) {
        val carToInsert = CarRequest(
                name = car.name,
                userId = userDataHolder.dataHolder.getLong(UserDataHolder.ID_KEY, -1),
                yearOfIssue = car.yearOfIssue,
                mileageInKm = car.mileageInKm,
                automobileBody = car.automobileBody,
                color = car.color,
                carEngine = CarEngineRequest(
                        cylinderVolumeInLitres = car.engine.cylinderVolumeInLitres,
                        enginePower = car.engine.enginePower,
                        type = car.engine.type
                ),
                taxPerYear = car.taxPerYear,
                transmissionType = car.transmissionType,
                typeOfDriverUnit = car.typeOfDriveUnit,
                steeringWheelLocation = car.steeringWheelLocation,
                vin = car.vin,
                stateNumber = car.stateNumber,
                description = car.description
        )
        api.updateCar(carToInsert, car.id)
    }

    override suspend fun getUserCars(): List<Car> =
        api.getCarsByUserID(userDataHolder.dataHolder.getLong(UserDataHolder.ID_KEY, -1)).map {
            Car(id = it.id,
            name = it.name,
            yearOfIssue = it.yearOfIssue,
            mileageInKm = it.mileageInKm,
            automobileBody = it.automobileBody,
            color = it.color,
            engine = CarEngine(
                    cylinderVolumeInLitres = it.carEngine.cylinderVolumeInLitres,
                    enginePower = it.carEngine.enginePower,
                    type = it.carEngine.type
            ),
            taxPerYear = it.taxPerYear,
            transmissionType = it.transmissionType,
            typeOfDriveUnit = it.typeOfDriverUnit,
            steeringWheelLocation = it.steeringWheelLocation,
            vin = it.vin,
            stateNumber = it.stateNumber,
            description = it.description)
        }



}
