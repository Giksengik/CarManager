package ru.vlasov.carmanager.utils

class StringToDoubleConverter {

    fun convert(string : String) : Double? {
        return if(string.replace("\\s+","") == "") 0.0
        else string.toDoubleOrNull()
    }
}