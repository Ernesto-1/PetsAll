package com.example.petsall.data.remote.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAHomeDatasource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    suspend fun getDataUser(): DocumentSnapshot? {
        return firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).get()
            .await()
    }

    suspend fun getDataPets(): List<DocumentSnapshot?> {
        val userPetsCollection = firebaseFirestore.collection("Users")
            .document(firebaseAuth.uid.toString())
            .collection("Mascotas")

        return try {
            val dataPets = userPetsCollection.get().await()
            dataPets.documents
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun deleteDataPet(idPet: String): Boolean {
        return try {
            val petRef = firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString())
                .collection("Mascotas").document(idPet)

            val cartillaRef = petRef.collection("Cartilla").get().await()
            val citasRef =
                firebaseFirestore.collection("Citas").whereEqualTo("patient", idPet).get().await()

            // Delete documents in Cartilla collection
            for (document in cartillaRef.documents) {
                document.reference.delete()
            }

            // Delete documents in Citas collection
            for (document in citasRef.documents) {
                document.reference.delete()
            }

            // Delete pet document
            petRef.delete().await()

            // Delete image from storage
            val storageRef =
                storage.reference.child("images/${firebaseAuth.uid.toString()}/pets/$idPet/$idPet")
            storageRef.delete()

            true
        } catch (e: Exception) {
            false
        }
    }
   suspend fun getDatePet(): List<DocumentSnapshot?> {
        val datePetQuery = firebaseFirestore.collection("Citas")
            .whereIn("status", listOf("pendiente", "confirmado"))
            .whereEqualTo("userId", firebaseAuth.uid.toString())

        return try {
            val datePet = datePetQuery.get().await()
            datePet.documents
        } catch (e: Exception) {
            emptyList()
        }
    }
}