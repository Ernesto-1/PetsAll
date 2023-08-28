package com.example.petsall.data.remote.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class PetsDataClass(
    val mascotas: MutableList<PetData> = mutableListOf()
)
data class PetData(
    val name: String? = "",
    val pet: String? = "",
    val breed: String? = "",
    val img: String? = "",
    val id: String? = "",
    val birthdate: Timestamp? = null
)
fun List<DocumentSnapshot?>.mapToPetsDataClass(): PetsDataClass {
    val petDataList = mapNotNull { documentSnapshot ->
        documentSnapshot.petData()
    }
    return PetsDataClass(petDataList.toMutableList())
}

fun DocumentSnapshot?.petData(): PetData? {
    if (this == null || !exists()) {
        return null
    }

    val petName = getString("Nombre") ?: ""
    val pet = getString("Mascota") ?: ""
    val breed = getString("Raza") ?: ""
    val img = getString("ImgUrl") ?: ""
    val id = getString("id") ?: ""
    val birthdate = getTimestamp("Fecha_Nacimiento")

    return PetData(petName,pet,breed,img,id,birthdate)
}





