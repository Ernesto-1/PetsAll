package com.example.petsall.domain.vetdetail

import com.example.petsall.data.remote.vetdetail.PAVetDetailDataSource
import com.example.petsall.data.remote.vetdetail.model.Coordinates
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAVetDetailRepoImpl @Inject constructor(private val datasource: PAVetDetailDataSource) :
    PAVetDetailRepo {
    override suspend fun getDataFromAddress(address: String): Coordinates =
        datasource.getCoordinator(address = address)

    override suspend fun getVet(id: String): DocumentSnapshot = datasource.getVet(id = id)
    override suspend fun registerDate(
        day: Timestamp?,
        patient: String,
        reason: String,
        idVet: String,
        consultorio: String
    ): Boolean = datasource.registerDate(
            day = day, patient = patient, reason = reason, idVet = idVet,
            vetName = consultorio
        )

    override suspend fun getDataPets(): List<DocumentSnapshot?> = datasource.getDataPets()
}