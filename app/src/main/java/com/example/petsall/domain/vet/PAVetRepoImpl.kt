package com.example.petsall.domain.vet

import com.example.petsall.data.remote.vet.PAVetDataSource
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAVetRepoImpl @Inject constructor(private val dataSource: PAVetDataSource): PAVetRepo {
    override suspend fun getVet(): List<DocumentSnapshot?> = dataSource.getVet()
}