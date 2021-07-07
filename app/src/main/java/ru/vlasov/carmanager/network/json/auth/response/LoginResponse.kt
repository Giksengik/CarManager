package ru.vlasov.carmanager.network.json.auth.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginResponse (@SerialName("token") val token : String?,
    @SerialName("type") val type : String?,
    @SerialName("id") val id : Long?,
    @SerialName("username") val username: String?,
    @SerialName("email") val email: String?,
    @SerialName("roles") val roles : List<String>?)