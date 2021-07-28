package ru.vlasov.carmanager.models


data class Car (val id : Long = 0, val name : String,
                val yearOfIssue: String, val mileageInKm : Double,
                val automobileBody : String, val color : String,
                val engine : CarEngine, val taxPerYear : Double,
                val transmissionType : String, val typeOfDriveUnit : String,
                val steeringWheelLocation : String, val vin : String,
                val stateNumber : String, val description : String)
