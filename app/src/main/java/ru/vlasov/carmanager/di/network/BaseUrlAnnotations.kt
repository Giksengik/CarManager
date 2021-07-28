package ru.vlasov.carmanager.di.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DebugBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainBaseUrl
