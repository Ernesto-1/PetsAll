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

    suspend fun deleteDataPet(idPet: String): Boolean {
        firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").document(idPet).delete().await()
        val deleteInfo = firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").document(idPet).collection("Cartilla").get().await()
        for (document in deleteInfo) {
            firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").document(idPet).collection("Cartilla").document(document.id).delete().await()
        }
        val deleteDates = firebaseFirestore.collection("Citas").whereEqualTo("patient",idPet).get().await()
        for (document in deleteDates) {
            firebaseFirestore.collection("Citas").document(document.id).delete().await()
        }
        return true
    }

    suspend fun getDatePet(idPet: String): List<DocumentSnapshot?> {
        val datePet = firebaseFirestore.collection("Citas").whereIn("status", listOf("pendiente", "confirmado"))
            .whereEqualTo("patient", idPet).get().await()
        return datePet.documents
    }
}