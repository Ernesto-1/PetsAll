package com.example.petsall.data.remote.consultingroom.vet

import android.location.Location
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAVetDataSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore) {

    suspend fun getVet(userLocate: Location): List<DocumentSnapshot?> {
        val businessCollection = firebaseFirestore.collection("Consultorios")
        val userLocation = GeoPoint(userLocate.latitude, userLocate.longitude)
        val maxDistanceKm = 5.0

        val query = businessCollection
            .whereEqualTo("status", "abierto")
            .whereLessThanOrEqualTo("ubicacion", GeoPoint(
                userLocation.latitude + (maxDistanceKm / 111.12),
                userLocation.longitude + (maxDistanceKm / 111.12)
            ))
            .whereGreaterThanOrEqualTo("ubicacion", GeoPoint(
                userLocation.latitude - (maxDistanceKm / 111.12),
                userLocation.longitude - (maxDistanceKm / 111.12)
            ))

        return try {
            val dataPets = query.get().await()
            dataPets.documents
        }catch(e: Exception)  {
            emptyList()
        }
    }
}