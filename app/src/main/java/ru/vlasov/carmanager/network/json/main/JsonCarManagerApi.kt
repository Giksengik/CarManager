package ru.vlasov.carmanager.network.json.main


import retrofit2.http.*
import ru.vlasov.carmanager.network.json.main.request.CarRequest
import ru.vlasov.carmanager.network.json.main.response.CarResponse

interface JsonCarManagerApi {
    @POST("api/car/new")
    suspend fun addCar(@Body car : CarRequest)

    @GET("api/car/{id}")
    suspend fun getCarsByUserID(@Path("id") userId : Long) : List<CarResponse>

    @PUT("api/car/{id}")
    suspend fun updateCar(@Body car : CarRequest, @Path("id") carId : Long)
}
