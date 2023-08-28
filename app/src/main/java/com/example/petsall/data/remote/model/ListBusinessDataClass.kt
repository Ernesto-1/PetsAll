package com.example.petsall.data.remote.model

import com.google.firebase.firestore.DocumentSnapshot

data class ListBusinessDataClass(
    val listBusiness: MutableList<BusinessData> = mutableListOf()
)

data class BusinessData(
    val accessories: Boolean = false,
    val food: Boolean = false,
    val articles : ArrayList<*>? = null,
    val latitude: Double = 19.305152,
    val length: Double = -99.186587,
    val name: String? = "",
    val desserts: Boolean = false,
    val status: String? = "",
    val imgBanner: String? = ""
)

fun List<DocumentSnapshot?>.mapToListBusinessDataClass(): ListBusinessDataClass {
    val petDataList = mapNotNull { documentSnapshot ->
        documentSnapshot.businessData()
    }
    return ListBusinessDataClass(petDataList.toMutableList())
}

fun DocumentSnapshot?.businessData(): BusinessData? {
    if (this == null || !exists()) {
        return null
    }

    val accessories = getBoolean("accesorios") ?: false
    val food = getBoolean("alimento") ?: false
    val articles = get("articulos") as? ArrayList<*>
    val latitude = getDouble("latitud") ?: 19.305152
    val length = getDouble("longitud") ?: -99.186587
    val name = getString("nombre") ?: ""
    val desserts =  getBoolean("postres") ?: false
    val status = getString("status") ?: ""
    val imgBanner = getString("imgBanner") ?: ""

    return BusinessData(accessories, food, articles, latitude, length, name,desserts,status,imgBanner)
}
