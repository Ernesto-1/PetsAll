package com.example.petsall.data.remote.business

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PABusinessDatasource @Inject constructor(private val firebaseFirestore: FirebaseFirestore){
    suspend fun getDataBusiness(nameListBusiness: String): List<DocumentSnapshot?> {
        val businessCollection = firebaseFirestore.collection("Negocios").whereEqualTo(nameListBusiness,true)

        return try {
            val dataPets = businessCollection.get().await()
            dataPets.documents
        } catch (e: Exception) {
            emptyList()
        }
    }

}