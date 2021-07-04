package ru.vlasov.carmanager.network.json.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LoginEntity(@SerialName("username") val username : String,
                  @SerialName("password") val password : String)