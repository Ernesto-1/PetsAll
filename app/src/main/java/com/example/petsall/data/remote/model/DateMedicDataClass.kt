package com.example.petsall.data.remote.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class DateMedicDataClass(
    val datesMedic: MutableList<PetDateMedic> = mutableListOf()
)

data class PetDateMedic(
    val status: String? = "",
    val reason: String? = "",
    val patient: String = "",
    val idConsult: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val nameVet: String = "",
    val dateMedic: Timestamp? = null,
    val nameConsult: String = "",
    val idPatient: String? = "",
    val id: String

)

fun List<DocumentSnapshot?>.mapToDateMedicDataClass(): DateMedicDataClass {
    val petDateMedicList = mapNotNull { documentSnapshot ->
        documentSnapshot.petDateMedic()
    }
    return DateMedicDataClass(petDateMedicList.toMutableList())
}

fun DocumentSnapshot?.petDateMedic(): PetDateMedic? {
    if (this == null || !exists()) {
        return null
    }
    val referenceId = this.reference.id
    val status = getString("status") ?: ""
    val reason = getString("reason") ?: ""
    val idPatient = getString("idPatient") ?: ""
    val patient = getString("patient") ?: ""
    val idConsult = getString("idConsultorio") ?: ""
    val dateMedic = getTimestamp("citaSolicitada")
    val lat = getDouble("latitud") ?: 0.0
    val long = getDouble("longitud") ?: 0.0
    val nameVet = getString("nombreVet") ?: ""
    val nameConsult = getString("nombreConsultorio") ?: ""

    return PetDateMedic(status,reason,patient,idConsult,lat,long,nameVet,dateMedic,nameConsult,idPatient,referenceId)
}



