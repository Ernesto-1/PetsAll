package com.example.petsall.data.remote.vet

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAVetDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    suspend fun getVet(): List<DocumentSnapshot?> {
        val getVets = firebaseFirestore.collection("Consultorios").get().await()
        return getVets.documents
    }

}