package ru.vlasov.carmanager.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.vlasov.carmanager.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataHolder @Inject constructor(@ApplicationContext context: Context) {
    var dataHolder : SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.user_data_shared_pref), Context.MODE_PRIVATE)
    companion object{
        const val TOKEN_KEY = "USER_DATA_TOKEN_KEY"
        const val TYPE_KEY = "USER_DATA_TYPE_KEY"
        const val ID_KEY = "USER_DATA_ID_KEY"
        const val USERNAME_KEY = "USER_DATA_USERNAME_KEY"
        const val EMAIL_KEY = "USER_DATA_EMAIL_KEY"
    }
}
