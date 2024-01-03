package com.example.petsall.domain.consultingroom.vetdetail

import com.example.petsall.data.remote.consultingroom.vetdetail.PAVetDetailDataSource
import com.example.petsall.data.remote.consultingroom.vetdetail.model.Coordinates
import com.example.petsall.presentation.model.RegisterDataRequest
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAVetDetailRepoImpl @Inject constructor(private val datasource: PAVetDetailDataSource) :
    PAVetDetailRepo {
    override suspend fun getDataFromAddress(address: String): Coordinates = datasource.getCoordinator(address = address)
    override suspend fun getVet(id:String): DocumentSnapshot  = datasource.getVet(id = id)
    override suspend fun registerDate(data: RegisterDataRequest): Boolean = datasource.registerDate(data)
    override suspend fun getDataPets(): List<DocumentSnapshot?> = datasource.getDataPets()
}