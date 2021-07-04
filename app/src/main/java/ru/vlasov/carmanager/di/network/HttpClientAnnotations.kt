package ru.vlasov.carmanager.di.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DebugHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainHttpClient