package ru.vlasov.carmanager

import android.app.AlertDialog

interface NetworkUser {
    fun showNetworkErrorDialog()
    fun showUserErrorDialog()
}
