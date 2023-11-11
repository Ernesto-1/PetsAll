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
    val idVet: String = "",
    val dateMedic: Timestamp? = null,
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
    val patient = getString("patient") ?: ""
    val idVet = getString("idVeterinaria") ?: ""
    val dateMedic = getTimestamp("day")

    return PetDateMedic(status, reason, patient, idVet, dateMedic, referenceId)
}



