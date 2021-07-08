package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentNewCarBinding
import ru.vlasov.carmanager.features.CurrentFragmentHolder
import ru.vlasov.carmanager.features.bottom_nav.BottomNavigationHolder

class FragmentNewCar : Fragment() {

    var binding : FragmentNewCarBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewCarBinding.inflate(inflater)
        defineLists()

        return binding?.root
    }

    private fun defineLists() {

        binding?.typeOfAutomobileBodyList?.apply {
            val array = resources.getStringArray(R.array.typesOfAutomobileBody)
            adapter = AttributeListAdapter(array)
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.typeOfTransmissionList?.apply{
            adapter = AttributeListAdapter(resources.getStringArray(R.array.typesOfTransmission))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.wheelLocationList?.apply{
            adapter = AttributeListAdapter(resources.getStringArray(R.array.wheelLocations))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.engineTypeList?.apply{
            adapter = AttributeListAdapter(resources.getStringArray(R.array.typesOfEngine))
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onResume() {
        super.onResume()
        configureParent()
    }

    private fun configureParent(){
        activity?.let{ activity ->
            (activity as CurrentFragmentHolder).getCurrentFragment()?.let{
                fragment -> (fragment as BottomNavigationHolder).hideBottomNav()
            }
        }
    }


}