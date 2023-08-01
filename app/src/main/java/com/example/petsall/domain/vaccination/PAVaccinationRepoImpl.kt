package com.example.petsall.domain.vaccination

import com.example.petsall.data.remote.vaccination.PAVaccinationDataSource
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAVaccinationRepoImpl @Inject constructor(private val dataSource: PAVaccinationDataSource) : PAVaccinationRepo  {

    override suspend fun getVaccinationList(idUser: String, idPet: String): List<DocumentSnapshot?> = dataSource.getVaccinationList(idUser = idUser, idPet = idPet)

}