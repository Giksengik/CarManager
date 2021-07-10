package ru.vlasov.carmanager.features

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.NetworkUser
import ru.vlasov.carmanager.R


@AndroidEntryPoint
class MainProvider : AppCompatActivity() , NavigationProvider, CurrentFragmentHolder, NetworkUser {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun navigateByAction(action: NavDirections) {
        findNavController(R.id.fragment_placeholder).navigate(action)
    }

    override fun showNetworkErrorDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.network_error_message)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun getCurrentFragment(): Fragment? {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_placeholder)
        return navHost?.childFragmentManager?.primaryNavigationFragment
    }

    fun configureNavDrawer(toolbar : Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)

    }

}