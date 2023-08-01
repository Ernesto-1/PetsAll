package com.example.petsall.data.remote.changepets

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAChangePetsDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getDataPets(selelectedPet: String): List<*>? {
        val listPets: List<*>?
        val dataPets =
            firebaseFirestore.collection("Tipo_mascotas").document("YPeGcxkpYMuVQ8xCE2kk").get()
                .await()
        listPets = dataPets.data?.get(selelectedPet) as List<*>
        return listPets
    }
}