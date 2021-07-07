package ru.vlasov.carmanager.features.bottom_nav.cars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.databinding.FragmentCarsBinding

@AndroidEntryPoint
class FragmentCars : Fragment() {

    var binding : FragmentCarsBinding? = null
    val viewModel : CarsViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarsBinding.inflate(inflater)

        val listAdapter = CarListAdapter()
        binding?.carList?.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        viewModel.userCars.observe(viewLifecycleOwner){
            listAdapter.submitList(it)
        }

        viewModel.getUserCars()

        return binding?.root
    }
}