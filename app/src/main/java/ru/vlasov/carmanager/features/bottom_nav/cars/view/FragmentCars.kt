package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentCarsBinding
import ru.vlasov.carmanager.features.CurrentFragmentHolder

import ru.vlasov.carmanager.features.NavigationProvider
import ru.vlasov.carmanager.features.bottom_nav.BottomNavigationHolder
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.CarDataRepresentationState
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.CarsViewModelImpl

@AndroidEntryPoint
class FragmentCars : Fragment() {

    var binding : FragmentCarsBinding? = null
    private val viewModel : CarsViewModelImpl by viewModels()
    private var listAdapter : CarListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarsBinding.inflate(inflater)
        listAdapter = CarListAdapter()
        binding?.carList?.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        binding?.buttonAddCar?.setOnClickListener{
            navigateToAddCarFragment()
        }

        viewModel.userCars.observe(viewLifecycleOwner){
            handleState(it)
        }

        viewModel.getUserCars()

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        configureParent()
    }

    private fun configureParent(){
        activity?.let{ activity ->
            (activity as CurrentFragmentHolder).getCurrentFragment()?.let{
                fragment -> (fragment as BottomNavigationHolder).showBottomNav()
            }
        }
    }


    private fun navigateToAddCarFragment() {
        val action = FragmentCarsDirections.actionFragmentHomeToFragmentNewCar()
        findNavController().navigate(action)
    }

    private fun handleState(state: CarDataRepresentationState?) {
        when(state){
            is CarDataRepresentationState.Loading -> setLoading()
            is CarDataRepresentationState.Error.NetworkError -> {
                setNotLoading()
                showNetworkErrorDialog()
            }
            is CarDataRepresentationState.Error.RequestError -> {
                setNotLoading()
                showUserErrorDialog()
            }
            is CarDataRepresentationState.CarsLoaded ->{
                setNotLoading()
                listAdapter?.submitList(state.cars)
            }
        }
    }

    private fun showUserErrorDialog() {
        AlertDialog.Builder(context)
                .setMessage(R.string.user_loading_error)
                .setPositiveButton(R.string.ok){
                    dialog, _ -> dialog.dismiss()
                }
                .show()

    }

    private fun showNetworkErrorDialog() {
        AlertDialog.Builder(context)
                .setMessage(R.string.network_error_message)
                .setPositiveButton(R.string.cancel){
                    dialog, _ -> dialog.dismiss()
                }
                .show()
    }

    private fun setLoading() {
        binding?.carListProgressBar?.visibility = View.VISIBLE
    }

    private fun setNotLoading() {
        binding?.carListProgressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        listAdapter = null
    }
}