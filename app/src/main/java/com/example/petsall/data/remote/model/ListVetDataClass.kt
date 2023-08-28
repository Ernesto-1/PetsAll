package com.example.petsall.data.remote.model

import com.google.firebase.firestore.DocumentSnapshot

data class ListVetDataClass(
    val vets: MutableList<VetData> = mutableListOf()
)
data class VetData(
    val name: String? = "",
    val lat: Double = 19.305152,
    val long: Double = -99.186587,
    val TimeStart: String? = "",
    val TimeEnd: String? = "",
    val status: String? = "",
    val id: String = "",
    val listSpecialties : ArrayList<*>? = null,
    val listSpecializedSector : ArrayList<*>? = null

)

fun List<DocumentSnapshot?>.mapToListVetDataClass(): ListVetDataClass {
    val vetDataList = mapNotNull { documentSnapshot ->
        documentSnapshot.vetData()
    }
    return ListVetDataClass(vetDataList.toMutableList())
}

fun DocumentSnapshot?.vetData(): VetData? {
    if (this == null || !exists()) {
        return null
    }

    val name = getString("Nombre") ?: ""
    val lat = getDouble("Latitud") ?: 19.305152
    val long = getDouble("Longitud") ?: -99.186587
    val timeStart = getString("HInicio") ?: ""
    val timeEnd = getString("HFin") ?: ""
    val status = getString("status") ?: ""
    val id = getString("Id") ?: ""
    val listSpecialties: ArrayList<*>? = get("medical_specialties") as? ArrayList<*>
    val listSpecializedSector: ArrayList<*>? = get("specialized_sector") as? ArrayList<*>

    return VetData(name = name,lat = lat, long = long, TimeStart = timeStart, TimeEnd = timeEnd, id = id,status = status,listSpecialties = listSpecialties,listSpecializedSector = listSpecializedSector)
}
