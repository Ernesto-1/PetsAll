package com.example.petsall.data.remote.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAHomeDatasource @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firebaseFirestore: FirebaseFirestore) {

    suspend fun getDataUser(): DocumentSnapshot? {
        return firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).get()
            .await()
    }

    suspend fun getDataPets(): List<DocumentSnapshot?> {
        val dataPets = firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").get().await()
        return dataPets.documents
    }

    suspend fun getDatePet(idPet: String): List<DocumentSnapshot?> {
        val datePet = firebaseFirestore.collection("Citas").whereIn("status", listOf("pendiente", "confirmado"))
            .whereEqualTo("patient", idPet).get().await()
        return datePet.documents
    }
}