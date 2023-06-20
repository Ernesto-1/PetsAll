package com.example.petsall.domain.changepets

import com.google.firebase.firestore.DocumentSnapshot

interface PAChangePetsRepo {
    suspend fun getDataPets(): List<DocumentSnapshot?>
}