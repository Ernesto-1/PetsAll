package com.example.petsall.data.remote.files

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAFilesDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore,private val firebaseAuth: FirebaseAuth) {

    suspend fun getFilesList(idPet: String): List<DocumentSnapshot?> {
        val vaccination =
            firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas")
                .document(idPet).collection("Expediente")
                .orderBy("Fecha", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get().await()
        return vaccination.documents
    }
}