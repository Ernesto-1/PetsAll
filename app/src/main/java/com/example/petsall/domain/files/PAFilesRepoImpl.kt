package com.example.petsall.domain.files

import com.example.petsall.data.remote.files.PAFilesDataSource
import com.example.petsall.domain.vaccination.PAVaccinationRepo
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAFilesRepoImpl @Inject constructor(private val dataSource: PAFilesDataSource) : PAFilesRepo {

    override suspend fun getFilesList(idPet: String): List<DocumentSnapshot?> = dataSource.getFilesList(idPet = idPet)

}