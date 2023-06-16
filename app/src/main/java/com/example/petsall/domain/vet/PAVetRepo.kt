package com.example.petsall.domain.vet

import com.google.firebase.firestore.DocumentSnapshot

interface PAVetRepo {

    suspend fun getVet():List<DocumentSnapshot?>
}