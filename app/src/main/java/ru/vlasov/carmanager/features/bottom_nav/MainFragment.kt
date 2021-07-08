package ru.vlasov.carmanager.features.bottom_nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections

import androidx.navigation.fragment.findNavController
import ru.vlasov.carmanager.databinding.FragmentMainBinding
import ru.vlasov.carmanager.features.NavigationProvider

class MainFragment : Fragment() , NavigationProvider, BottomNavigationHolder {

    var binding : FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        return binding?.root
    }

    override fun navigateByAction(action: NavDirections) {
        findNavController().navigate(action)
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
}