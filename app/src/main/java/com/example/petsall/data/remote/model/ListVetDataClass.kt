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
    val number: String = "",
    val imgLogo: String = "",
    val listSpecialties : ArrayList<*>? = null,
    val listImages : ArrayList<*>? = null,
    val listSpecializedSector : ArrayList<*>? = null

)


fun List<DocumentSnapshot?>.mapToListVetDataClass(): ListVetDataClass {
    val vetDataList = mapNotNull { documentSnapshot ->
        documentSnapshot.mapToVetData()
    }
    return ListVetDataClass(vetDataList.toMutableList())
}
