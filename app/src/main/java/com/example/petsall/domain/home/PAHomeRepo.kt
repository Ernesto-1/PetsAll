package com.example.petsall.domain.home

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface PAHomeRepo {
    suspend fun getDataUser(): DocumentSnapshot?
    suspend fun getDataPets(): QuerySnapshot?
}