package ru.vlasov.carmanager.di.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vlasov.carmanager.network.RemoteDataSource
import ru.vlasov.carmanager.network.json.DataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(dataSource : DataSource) : RemoteDataSource

}