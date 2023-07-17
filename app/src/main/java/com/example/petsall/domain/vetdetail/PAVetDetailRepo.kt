package com.example.petsall.domain.vetdetail

import com.example.petsall.data.remote.vetdetail.model.Coordinates
import com.google.firebase.firestore.DocumentSnapshot

interface PAVetDetailRepo {

    suspend fun getDataFromAddress(address: String):Coordinates

    suspend fun getVet(id: String):DocumentSnapshot

    suspend fun registerDate(day:String, time: String, patient: String, reason: String,idVet:String): Boolean


}