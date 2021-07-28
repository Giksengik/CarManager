package ru.vlasov.carmanager.network.json.main.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CarResponse (
        @SerialName("id") val id : Long,
        @SerialName("name") val name : String,
        @SerialName("year_of_issue") val yearOfIssue : String,
        @SerialName("mileage_in_km") val mileageInKm : Double,
        @SerialName("automobile_body") val automobileBody : String,
        @SerialName("color") val color : String,
        @SerialName("car_engine") val carEngine : CarEngineResponse,
        @SerialName("tax_per_year") val taxPerYear : Double,
        @SerialName("transmission_type") val transmissionType : String,
        @SerialName("type_of_driver_unit") val typeOfDriverUnit : String,
        @SerialName("steering_wheel_location") val steeringWheelLocation : String,
        @SerialName("VIN") val vin : String,
        @SerialName("state_number") val stateNumber : String,
        @SerialName("description") val description : String)
