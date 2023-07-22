package com.example.petsall.domain.emergency

import com.example.petsall.data.remote.emergency.PAEmergencyDataSource
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAEmergencyRepoImpl @Inject constructor (private val datasource: PAEmergencyDataSource): PAEmergencyRepo {
    override suspend fun getVet(): List<DocumentSnapshot?> = datasource.getVet()
}