package com.example.petsall.data.remote.model

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.GeoPoint

data class ListBusinessDataClass(
    val listBusiness: MutableList<BusinessData> = mutableListOf()
)

data class BusinessData(
    val articles : ArrayList<*>? = null,
    val latitude: Double = 19.305152,
    val length: Double = -99.186587,
    val name: String? = "",
    val status: String? = "",
    val imgBanner: String? = "",
    val hStart: String? = "",
    val hEnd: String? = "",
    val locationGeoPoint: GeoPoint? = null,
    val category:ArrayList<*>? = null,
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

    val articles = get("articulos") as? ArrayList<*>
    val latitude = getDouble("latitud") ?: 19.305152
    val length = getDouble("longitud") ?: -99.186587
    val name = getString("nombre") ?: ""
    val status = getString("status") ?: ""
    val imgBanner = getString("imgBanner") ?: ""
    val hStart = getString("hora_Inicio") ?: ""
    val hEnd = getString("hora_Fin") ?: ""
    val locationGeoPoint = getGeoPoint("ubicacion")
    val category = get("categoria") as? ArrayList<*>

    return BusinessData(articles, latitude, length, name,status,imgBanner,hStart, hEnd,locationGeoPoint,category)
}
