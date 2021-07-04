package ru.vlasov.carmanager.network.json.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MessageResponse(
    @SerialName("message") val message : String?)