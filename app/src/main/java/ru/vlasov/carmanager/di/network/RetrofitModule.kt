package ru.vlasov.carmanager.di.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.vlasov.carmanager.data.UserDataHolder
import ru.vlasov.carmanager.network.json.auth.JsonCarManagerAuthApi
import ru.vlasov.carmanager.network.json.main.JsonCarManagerApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private val CONNECT_TIMEOUT = 10L
    private val WRITE_TIMEOUT = 30L
    private val READ_TIMEOUT = 10L

    @Provides
    @DebugBaseUrl
    fun provideDebugBaseUrl() : String =
        "http://10.0.2.2:8080/"

    @Provides
    fun provideJson() : Json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @BearerTokenInterceptor
    fun provideBearerTokenInterceptor(userDataHolder : UserDataHolder): Interceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${userDataHolder.dataHolder.getString(UserDataHolder.TOKEN_KEY, "") ?: ""}")
                .build()
        chain.proceed(newRequest)
    }

    @MainHttpClient
    @Provides
    fun provideMainHttpClient(@BearerTokenInterceptor interceptor : Interceptor)  : OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .addNetworkInterceptor(HttpLoggingInterceptor().apply{
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .addInterceptor(interceptor)
                    .build()

    @AuthHttpClient
    @Provides
    fun provideAuthHttpClient() : OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply{
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()




    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(@AuthHttpClient httpClient: OkHttpClient, json: Json,
                            @DebugBaseUrl baseUrl: String) : Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()


    @Provides
    @MainRetrofit
    fun provideMainRetrofit(@MainHttpClient httpClient: OkHttpClient, json: Json,
                            @DebugBaseUrl baseUrl: String) : Retrofit =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                    .client(httpClient)
                    .build()


    @Provides
    @Singleton
    @AuthApi
    fun provideAuthApi (@AuthRetrofit retrofit: Retrofit) : JsonCarManagerAuthApi =
        retrofit.create(JsonCarManagerAuthApi::class.java)

    @Provides
    @Singleton
    @MainApi
    fun provideMainApi (@MainRetrofit retrofit: Retrofit) : JsonCarManagerApi =
            retrofit.create(JsonCarManagerApi::class.java)


}