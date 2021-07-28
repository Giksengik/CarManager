package ru.vlasov.carmanager.features.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections

import androidx.navigation.fragment.findNavController
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentMainBinding
import ru.vlasov.carmanager.features.MainProvider
import ru.vlasov.carmanager.features.NavigationProvider

class MainFragment : Fragment() , NavigationProvider, BottomNavigationHolder, ToolbarProvider {

    var binding : FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        configureToolbar()
        return binding?.root
    }

    override fun navigateByAction(action: NavDirections) {
        findNavController().navigate(action)
    }

    private fun configureToolbar() {
        (activity as MainProvider).configureNavDrawer(binding?.topAppBar!!)
        binding?.topAppBar?.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.search ->{
                    Toast.makeText(context,"this is search", Toast.LENGTH_LONG).show()
                    true
                }
                else -> true
            }
        }
    }




    override fun hideBottomNav(){
        binding?.bttmNav?.visibility = View.GONE
    }

    override fun showBottomNav(){
        binding?.bttmNav?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun getToolbar(): Toolbar {
        return binding?.topAppBar!!
    }

    override fun setUpNavigationDrawer() {
        (activity as MainProvider).configureNavDrawer(binding?.topAppBar!!)
    }
}