package ru.vlasov.carmanager.features.bottom_nav.cars.view


import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.NetworkUser
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentNewCarBinding
import ru.vlasov.carmanager.features.CurrentFragmentHolder
import ru.vlasov.carmanager.features.bottom_nav.BottomNavigationHolder
import ru.vlasov.carmanager.features.bottom_nav.ToolbarProvider
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.CarCreatingState
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.NewCarViewModelImpl
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.models.CarEngine
import ru.vlasov.carmanager.utils.StringToDoubleConverter
import kotlinx.coroutines.launch
import ru.vlasov.carmanager.features.SuccessAnimationProvider

@AndroidEntryPoint
class FragmentNewCar : Fragment() {

    private val viewModel : NewCarViewModelImpl by viewModels()
    var binding : FragmentNewCarBinding? = null
    var automobileBodyListAdapter : AttributeListAdapter? = null
    var typesOfTransmissionListAdapter : AttributeListAdapter? = null
    var wheelLocationsListAdapter : AttributeListAdapter? = null
    var engineTypesListAdapter : AttributeListAdapter? = null
    var driveUnitTypesListAdapter : AttributeListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewCarBinding.inflate(inflater)
        configureToolbar()
        defineLists()
        viewModel.viewState.observe(viewLifecycleOwner){
            handleState(it)
        }
        return binding?.root
    }

    private fun handleState(state: CarCreatingState?) =
            when(state){
              is CarCreatingState.Loading -> setLoading()
                is CarCreatingState.Success -> onSuccess()
                is CarCreatingState.Error.RequestError -> onRequestError()
                is CarCreatingState.Error.NetworkError -> onNetworkError()
                else -> onUnexpectedState()
            }

    private fun onUnexpectedState(){
        setLoaded()
    }

    private fun onNetworkError(){
        setLoaded()
        activity?.let{
            (it as NetworkUser).showNetworkErrorDialog()
        }
    }

    private fun onRequestError(){
        setLoaded()
        activity?.let{
            (it as NetworkUser).showUserErrorDialog()
        }
    }

    private fun onSuccess(){
        activity?.let{
            (it as SuccessAnimationProvider).provideSuccessAnimation(object : Animator.AnimatorListener{
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    navigateToCarsList()
                    setLoaded()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
        }
    }

    private fun setLoading(){
        binding?.carCreatingProgressBar?.visibility = View.VISIBLE
        binding?.newCarColorField?.isEnabled = false
        binding?.newCarCylinderVolumeInLitresField?.isEnabled = false
        binding?.newCarDescriptionField?.isEnabled = false
        binding?.newCarEnginePowerField?.isEnabled = false
        binding?.newCarMileageInKmField?.isEnabled = false
        binding?.newCarNameField?.isEnabled = false
        binding?.newCarTaxPerYearField?.isEnabled = false
        binding?.newCarVINField?.isEnabled = false
        binding?.newCarYearOfIssueField?.isEnabled = false
        binding?.newStateNumberField?.isEnabled = false
        binding?.driverUnitTypesList?.isEnabled = false
        binding?.engineTypeList?.isEnabled = false
        binding?.wheelLocationList?.isEnabled = false
        binding?.driverUnitTypesList?.isEnabled = false
        binding?.typeOfTransmissionList?.isEnabled = false
        binding?.typeOfAutomobileBodyList?.isEnabled = false
        lockToolbar()
    }

    private fun lockToolbar() {
        activity?.let { activity ->
            (activity as CurrentFragmentHolder).getCurrentFragment()?.let { fragment ->
                val toolbar = (fragment as ToolbarProvider).getToolbar()
                toolbar.findViewById<View>(R.id.confirm).isEnabled = false
            }
        }
    }

    private fun unlockToolbar() {
        activity?.let { activity ->
            (activity as CurrentFragmentHolder).getCurrentFragment()?.let { fragment ->
                val toolbar = (fragment as ToolbarProvider).getToolbar()
                toolbar.findViewById<View>(R.id.confirm).isEnabled = true
            }
        }
    }

    private fun setLoaded() {
        binding?.carCreatingProgressBar?.visibility = View.GONE
        binding?.newCarColorField?.isEnabled = true
        binding?.newCarCylinderVolumeInLitresField?.isEnabled = true
        binding?.newCarDescriptionField?.isEnabled = true
        binding?.newCarEnginePowerField?.isEnabled = true
        binding?.newCarMileageInKmField?.isEnabled = true
        binding?.newCarNameField?.isEnabled = true
        binding?.newCarTaxPerYearField?.isEnabled = true
        binding?.newCarVINField?.isEnabled = true
        binding?.newCarYearOfIssueField?.isEnabled = true
        binding?.newStateNumberField?.isEnabled = true
        binding?.driverUnitTypesList?.isEnabled = true
        binding?.engineTypeList?.isEnabled = true
        binding?.wheelLocationList?.isEnabled = true
        binding?.driverUnitTypesList?.isEnabled = true
        binding?.typeOfTransmissionList?.isEnabled = true
        binding?.typeOfAutomobileBodyList?.isEnabled = true
        unlockToolbar()
    }

    private fun configureToolbar() {
        activity?.let { activity ->
            (activity as CurrentFragmentHolder).getCurrentFragment()?.let { fragment ->
                val toolbar = (fragment as ToolbarProvider).getToolbar()
                toolbar.title = getString(R.string.new_car)
                toolbar.findViewById<View>(R.id.addItem).visibility = View.GONE
                toolbar.findViewById<View>(R.id.search).visibility = View.GONE
                toolbar.findViewById<View>(R.id.confirm).apply {
                    visibility = View.VISIBLE
                    setOnClickListener{
                        clearHelpers()
                        if (isInputCorrect()) {
                            addNewCar()
                        }
                        else
                            checkAndShowInputError()
                    }
                }
                toolbar.navigationIcon = ResourcesCompat.getDrawable(resources,R.drawable.ic_backspace, activity.application?.theme)
                toolbar.setNavigationOnClickListener{
                    navigateToCarsList()
                }

            }
        }
    }

    private fun navigateToCarsList() {
        val action = FragmentNewCarDirections.actionFragmentNewCarToFragmentHome()
        findNavController().navigate(action)
    }


    private fun isInputCorrect(): Boolean {
        val converter = StringToDoubleConverter()
        return converter.convert((binding?.newCarMileageInKmField?.text.toString())) != null &&
                converter.convert((binding?.newCarCylinderVolumeInLitresField?.text.toString())) != null &&
                converter.convert((binding?.newCarTaxPerYearField?.text.toString())) != null &&
                converter.convert((binding?.newCarEnginePowerField?.text.toString())) != null
    }
    private fun checkAndShowInputError() {
        val converter = StringToDoubleConverter()
        if (converter.convert((binding?.newCarMileageInKmField?.text.toString())) == null)
            showCarMileageInputError()
        if (converter.convert((binding?.newCarCylinderVolumeInLitresField?.text.toString())) == null)
            showCylinderVolumeInputError()
        if (converter.convert((binding?.newCarTaxPerYearField?.text.toString())) == null)
            showTaxInputError()
        if (converter.convert((binding?.newCarEnginePowerField?.text.toString())) == null)
            showEnginePowerInputError()
    }

    private fun showEnginePowerInputError() {
        binding?.newCarEnginePowerBlock?.helperText = getString(R.string.wrong_input)
    }

    private fun showTaxInputError() {
        binding?.newCarCylinderVolumeInLitresBlock?.helperText = getString(R.string.wrong_input)
    }

    private fun showCylinderVolumeInputError() {
        binding?.newCarTaxPerYearBlock?.helperText = getString(R.string.wrong_input)
    }

    private fun showCarMileageInputError() {
        binding?.newCarMileageInKmBlock?.helperText = getString(R.string.wrong_input)
    }

    private fun clearHelpers(){
        binding?.newCarEnginePowerBlock?.helperText = ""
        binding?.newCarCylinderVolumeInLitresBlock?.helperText = ""
        binding?.newCarTaxPerYearBlock?.helperText = ""
        binding?.newCarTaxPerYearBlock?.helperText = ""
    }

    private fun addNewCar() {
        val converter = StringToDoubleConverter()
        viewModel.addNewCar(Car(
                name = binding?.newCarNameField?.text.toString(),
                yearOfIssue = binding?.newCarYearOfIssueField?.text.toString(),
                mileageInKm = converter.convert(binding?.newCarMileageInKmField?.text.toString())!!,
                automobileBody = automobileBodyListAdapter?.getCheckedItem() ?: "",
                color = binding?.newCarColorField?.text.toString(),
                engine = CarEngine(
                        cylinderVolumeInLitres = converter.convert(binding?.newCarCylinderVolumeInLitresField?.text.toString())!!,
                        enginePower = converter.convert(binding?.newCarEnginePowerField?.text.toString())!!,
                        type = engineTypesListAdapter?.getCheckedItem() ?: ""
                ),
                taxPerYear = converter.convert(binding?.newCarTaxPerYearField?.text.toString())!!,
                transmissionType = typesOfTransmissionListAdapter?.getCheckedItem() ?: "",
                typeOfDriveUnit = driveUnitTypesListAdapter?.getCheckedItem() ?: "",
                steeringWheelLocation = wheelLocationsListAdapter?.getCheckedItem() ?: "",
                VIN = binding?.newCarVINField?.text.toString(),
                stateNumber = binding?.newStateNumberField?.text.toString(),
                description = binding?.newCarDescriptionField?.text.toString()
        ))
    }

    private fun defineLists() {
        automobileBodyListAdapter = AttributeListAdapter(resources.getStringArray(R.array.typesOfAutomobileBody))
        typesOfTransmissionListAdapter = AttributeListAdapter(resources.getStringArray(R.array.typesOfTransmission))
        wheelLocationsListAdapter = AttributeListAdapter(resources.getStringArray(R.array.wheelLocations))
        engineTypesListAdapter = AttributeListAdapter(resources.getStringArray(R.array.typesOfEngine))
        driveUnitTypesListAdapter = AttributeListAdapter(resources.getStringArray(R.array.typesOfDriveUnit))
        binding?.typeOfAutomobileBodyList?.apply {
            adapter = automobileBodyListAdapter
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.typeOfTransmissionList?.apply{
            adapter = typesOfTransmissionListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.wheelLocationList?.apply{
            adapter = wheelLocationsListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.engineTypeList?.apply{
            adapter = engineTypesListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding?.driverUnitTypesList?.apply{
            adapter = driveUnitTypesListAdapter
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

    override fun onDestroy() {
        super.onDestroy()
        automobileBodyListAdapter = null
        typesOfTransmissionListAdapter = null
        wheelLocationsListAdapter = null
        engineTypesListAdapter = null

    }


}