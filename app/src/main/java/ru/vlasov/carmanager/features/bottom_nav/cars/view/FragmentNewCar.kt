package ru.vlasov.carmanager.features.bottom_nav.cars.view

import android.graphics.drawable.GradientDrawable
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.vlasov.carmanager.R
import ru.vlasov.carmanager.databinding.FragmentNewCarBinding
import ru.vlasov.carmanager.features.CurrentFragmentHolder
import ru.vlasov.carmanager.features.bottom_nav.BottomNavigationHolder
import ru.vlasov.carmanager.features.bottom_nav.cars.viewmodel.NewCarViewModelImpl
import ru.vlasov.carmanager.models.Car
import ru.vlasov.carmanager.models.CarEngine
import ru.vlasov.carmanager.network.json.main.request.CarRequest

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
        defineLists()
        binding?.addCarButton?.setOnClickListener{
            addNewCar()
        }
        return binding?.root
    }

    private fun addNewCar() {
        viewModel.addNewCar(Car(
                name = binding?.newCarNameField?.text.toString(),
                yearOfIssue = binding?.newCarYearOfIssueField?.text.toString(),
                mileageInKm = Integer.parseInt(binding?.newCarMileageInKmField?.text.toString()),
                automobileBody = automobileBodyListAdapter?.getCheckedItem() ?: "",
                color = binding?.newCarColorField?.text.toString(),
                engine = CarEngine(
                        cylinderVolumeInLitres = Integer.parseInt(binding?.newCarCylinderVolumeInLitresField?.text.toString()),
                        enginePower = Integer.parseInt(binding?.newCarEnginePowerField?.text.toString()),
                        type = engineTypesListAdapter?.getCheckedItem() ?: ""
                ),
                taxPerYear = Integer.parseInt(binding?.newCarTaxPerYearField?.text.toString()),
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