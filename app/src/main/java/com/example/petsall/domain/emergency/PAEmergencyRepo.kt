package com.example.petsall.domain.emergency

import com.google.firebase.firestore.DocumentSnapshot

interface PAEmergencyRepo {

    suspend fun getVet():List<DocumentSnapshot?>

}