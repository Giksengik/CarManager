package ru.vlasov.carmanager.features

import androidx.navigation.NavDirections

interface NavigationProvider {
    fun navigateByAction(action : NavDirections)
}