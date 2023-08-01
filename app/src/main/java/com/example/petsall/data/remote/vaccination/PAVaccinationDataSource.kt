package com.example.petsall.data.remote.vaccination

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAVaccinationDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    suspend fun getVaccinationList(idUser: String, idPet: String): List<DocumentSnapshot?> {
        val vaccination =
            firebaseFirestore.collection("Users").document(idUser).collection("Mascotas")
                .document(idPet).collection("Cartilla")
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get().await()
        return vaccination.documents
    }
}