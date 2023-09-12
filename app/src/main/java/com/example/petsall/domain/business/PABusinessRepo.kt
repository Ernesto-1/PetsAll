package com.example.petsall.domain.business

import com.google.firebase.firestore.DocumentSnapshot

interface PABusinessRepo {
    suspend fun getDataBusiness(nameListBusiness: String): List<DocumentSnapshot?>
}