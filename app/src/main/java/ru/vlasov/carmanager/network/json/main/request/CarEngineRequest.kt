package ru.vlasov.carmanager.network.json.main.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CarEngineRequest(
        @SerialName("cylinder_volume_in_litres") val cylinderVolumeInLitres : Int,
        @SerialName("engine_power") val enginePower : Int,
        @SerialName("type") val type : String)
