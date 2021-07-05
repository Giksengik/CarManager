package ru.vlasov.carmanager.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import ru.vlasov.carmanager.repositories.AuthRepository
import ru.vlasov.carmanager.repositories.AuthRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
            authRepositoryImpl: AuthRepositoryImpl
    ) : AuthRepository

}