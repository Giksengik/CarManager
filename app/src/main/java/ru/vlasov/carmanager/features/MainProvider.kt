package ru.vlasov.carmanager.features

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.NetworkUser
import ru.vlasov.carmanager.R


@AndroidEntryPoint
class MainProvider : AppCompatActivity() , NavigationProvider, CurrentFragmentHolder, NetworkUser, SuccessAnimationProvider {
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

    override fun showUserErrorDialog() {
        fun showUserErrorDialog() {
            android.app.AlertDialog.Builder(this)
                    .setMessage(R.string.user_loading_error)
                    .setPositiveButton(R.string.ok){
                        dialog, _ -> dialog.dismiss()
                    }
                    .show()

        }
    }

    override fun getCurrentFragment(): Fragment? {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_placeholder)
        return navHost?.childFragmentManager?.primaryNavigationFragment
    }

    fun configureNavDrawer(toolbar : Toolbar){
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)

    }

    override fun provideSuccessAnimation(animatorListener: Animator.AnimatorListener) {
        val anim = findViewById<LottieAnimationView>(R.id.successAnim)
        anim.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) { anim.visibility = View.GONE }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
        anim.visibility = View.VISIBLE
        anim.addAnimatorListener(animatorListener)
        anim.playAnimation()
    }

}