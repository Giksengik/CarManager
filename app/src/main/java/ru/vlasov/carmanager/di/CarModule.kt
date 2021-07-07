package ru.vlasov.carmanager.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vlasov.carmanager.repositories.CarRepository
import ru.vlasov.carmanager.repositories.CarRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CarModule {

    @Binds
    @Singleton
    abstract fun getCarRepository(carRepository: CarRepositoryImpl) : CarRepository
}