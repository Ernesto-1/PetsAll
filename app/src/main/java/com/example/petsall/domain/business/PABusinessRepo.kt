package com.example.petsall.domain.business

import android.location.Location
import com.google.firebase.firestore.DocumentSnapshot

interface PABusinessRepo {
    suspend fun getDataBusiness(userLocate: Location): List<DocumentSnapshot?>
}