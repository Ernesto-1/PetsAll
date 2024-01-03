package com.example.petsall.domain.consultingroom.vet

import android.location.Location
import com.example.petsall.data.remote.consultingroom.vet.PAVetDataSource
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAVetRepoImpl @Inject constructor(private val dataSource: PAVetDataSource): PAVetRepo {
    override suspend fun getVet(userLocate: Location): List<DocumentSnapshot?> = dataSource.getVet(userLocate)
}