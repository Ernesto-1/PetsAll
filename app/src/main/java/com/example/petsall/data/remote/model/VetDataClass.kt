package com.example.petsall.data.remote.model

import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot?.mapToVetData(): VetData? {
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
    val number = getString("Numero") ?: ""
    val imgLogo = getString("imgLogo") ?: ""
    val listSpecialties: ArrayList<*>? = get("medical_specialties") as? ArrayList<*>
    val listImages: ArrayList<*>? = get("Imagenes") as? ArrayList<*>
    val listSpecializedSector: ArrayList<*>? = get("specialized_sector") as? ArrayList<*>

    return VetData(name = name,lat = lat, long = long, TimeStart = timeStart, TimeEnd = timeEnd, id = id, number = number,status = status,listSpecialties = listSpecialties, listImages = listImages,listSpecializedSector = listSpecializedSector, imgLogo = imgLogo)
}


