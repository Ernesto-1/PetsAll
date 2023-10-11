package com.example.petsall.data.remote.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class ListVaccineDataClass(
    val vaccines: MutableList<VaccineDataClass> = mutableListOf()
)

data class VaccineDataClass(
    val pLicense: String = "",
    val status: String = "",
    val type: String = "",
    val vaccine: String = "",
    val dateVaccine: Timestamp? = null,
    val vaccineExpiration: Timestamp? = null
)

fun DocumentSnapshot?.mapToVaccineDataClass(): VaccineDataClass? {
    if (this == null || !exists()) {
        return null
    }
    val pLicense = getString("CedulaP") ?: ""
    val type = getString("Tipo") ?: ""
    val vaccine = getString("Vacuna") ?: ""
    val status = getString("Estatus") ?: ""
    val vaccineExpiration = getTimestamp("Caducidad_vacuna")
    val dateVaccine = getTimestamp("Fecha_vacunacion")

    return VaccineDataClass(pLicense = pLicense, status = status,type = type, vaccine = vaccine,dateVaccine = dateVaccine, vaccineExpiration = vaccineExpiration)
}

fun List<DocumentSnapshot?>.mapToListVaccineDataClass(): ListVaccineDataClass {
    val vaccineDataList = mapNotNull { documentSnapshot ->
        documentSnapshot.mapToVaccineDataClass()
    }
    return ListVaccineDataClass(vaccineDataList.toMutableList())
}


