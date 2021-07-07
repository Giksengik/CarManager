package ru.vlasov.carmanager.network.json.main.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CarEngineResponse (
        @SerialName("cylinder_volume_in_litres") val cylinderVolumeInLitres : Int,
        @SerialName("engine_power") val enginePower : Int,
        @SerialName("type") val type : String)