package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentNewCarBinding
import ru.vlasov.carmanager.features.CurrentFragmentHolder
import ru.vlasov.carmanager.features.NavigationProvider
import ru.vlasov.carmanager.features.bottom_nav.BottomNavigationHolder
import ru.vlasov.carmanager.features.bottom_nav.ToolbarProvider
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.NewCarViewModelImpl
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.models.CarEngine
import ru.vlasov.carmanager.network.json.main.request.CarRequest
import ru.vlasov.carmanager.utils.StringToDoubleConverter

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
        return binding?.root
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
        binding?.newCarMileageInKmBlock?.helperText = getString(R.string.wrong_input)
    }

    private fun showTaxInputError() {
        binding?.newCarCylinderVolumeInLitresBlock?.helperText = getString(R.string.wrong_input)
    }

    private fun showCylinderVolumeInputError() {
        binding?.newCarTaxPerYearBlock?.helperText = getString(R.string.wrong_input)
    }

    private fun showCarMileageInputError() {
        binding?.newCarEnginePowerBlock?.helperText = ""
        binding?.newCarCylinderVolumeInLitresBlock?.helperText = ""
        binding?.newCarTaxPerYearBlock?.helperText = ""
        binding?.newCarTaxPerYearBlock?.helperText = ""

    }

    private fun clearHelpers(){
        binding?.newCarMileageInKmBlock?.helperText = getString(R.string.wrong_input)
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