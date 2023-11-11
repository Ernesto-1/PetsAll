package com.example.petsall.domain.vetdetail

import com.example.petsall.data.remote.vetdetail.model.Coordinates
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

interface PAVetDetailRepo {

    suspend fun getDataFromAddress(address: String): Coordinates

    suspend fun getVet(id: String): DocumentSnapshot

    suspend fun registerDate(
        day: Timestamp?,
        patient: String,
        reason: String,
        idVet: String,
        vetName: String
    ): Boolean?

    suspend fun getDataPets(): List<DocumentSnapshot?>

}