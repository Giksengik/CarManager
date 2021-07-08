package ru.vlasov.carmanager.network.json.main.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CarEngineResponse (
        @SerialName("cylinder_volume_in_litres") val cylinderVolumeInLitres : Double,
        @SerialName("engine_power") val enginePower : Double,
        @SerialName("type") val type : String)