package ru.vlasov.carmanager.di.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vlasov.carmanager.network.json.auth.RemoteAuthDataSource
import ru.vlasov.carmanager.network.json.auth.AuthDataSource
import ru.vlasov.carmanager.network.json.main.MainRemoteDataSource
import ru.vlasov.carmanager.network.json.main.RemoteDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteAuthDataSource(dataSource : AuthDataSource) : RemoteAuthDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(dataSource: MainRemoteDataSource) : RemoteDataSource
}
