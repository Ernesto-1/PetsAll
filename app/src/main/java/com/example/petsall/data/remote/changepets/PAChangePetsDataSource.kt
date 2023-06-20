package com.example.petsall.data.remote.changepets

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAChangePetsDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firebaseFirestore: FirebaseFirestore) {

    suspend fun getDataPets(): List<DocumentSnapshot?> {
        val dataPets = firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").get().await()
        return dataPets.documents
    }
}