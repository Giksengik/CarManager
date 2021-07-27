package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.NetworkUser
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentCarsBinding
import ru.vlasov.carmanager.features.CurrentFragmentHolder

import ru.vlasov.carmanager.features.bottom_nav.BottomNavigationHolder
import ru.vlasov.carmanager.features.bottom_nav.ToolbarProvider
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.CarDataRepresentationState
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.CarsViewModelImpl
import ru.vlasov.carmanager.models.Car

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

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureToolbar()
        configureParent()

        listAdapter = CarListAdapter (object : CarListAdapter.CarListener {
            override fun onCarClick(car: Car) {
                navigateToCarProfile(car)
            }
        })

        binding?.carList?.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }

        viewModel.userCars.observe(viewLifecycleOwner){
            handleState(it)
        }

        viewModel.getUserCars()

    }

    private fun navigateToCarProfile(car : Car) {
        val action = FragmentCarsDirections.actionFragmentHomeToFragmentCarProfile()
        findNavController().navigate(action)
    }

    private fun configureToolbar() {
        activity?.let{ activity ->
            (activity as CurrentFragmentHolder).getCurrentFragment()?.let{
                fragment ->
                val toolbar = (fragment as ToolbarProvider).getToolbar()
                (fragment as ToolbarProvider).setUpNavigationDrawer()
                toolbar.title = ""
                toolbar.findViewById<View>(R.id.search).visibility = View.VISIBLE
                toolbar.findViewById<View>(R.id.addItem).visibility = View.VISIBLE
                toolbar.findViewById<View>(R.id.confirm).visibility = View.GONE
                toolbar.setNavigationIcon(R.drawable.ic_menu)
                toolbar.setOnMenuItemClickListener{
                    when(it.itemId){
                        R.id.addItem -> {
                            navigateToAddCarFragment()
                            true
                        }
                        else -> true
                    }
                }
            }
        }
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
                listAdapter?.setItems(state.cars)
            }
        }
    }

    private fun showUserErrorDialog() {
        activity?.let{
            (it as NetworkUser).showUserErrorDialog()
        }
    }

    private fun showNetworkErrorDialog() {
        activity?.let{
            (it as NetworkUser).showNetworkErrorDialog()
        }
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