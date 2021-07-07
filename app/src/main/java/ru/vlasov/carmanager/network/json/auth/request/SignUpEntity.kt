package ru.vlasov.carmanager.network.json.auth.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SignUpEntity(@SerialName("username") val username : String,
                   @SerialName("password") val password : String,
                   @SerialName("email") val email : String)