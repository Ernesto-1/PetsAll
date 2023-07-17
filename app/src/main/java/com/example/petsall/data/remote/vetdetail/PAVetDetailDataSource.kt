package com.example.petsall.data.remote.vetdetail

import android.util.Log
import com.example.petsall.data.remote.vetdetail.model.Coordinates
import com.example.petsall.domain.WebService
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.log

class PAVetDetailDataSource @Inject constructor(
    private val webService: WebService,
    private val firebaseFirestore: FirebaseFirestore
) {

    private val ap = "AIzaSyCs3Rnu3xpOWiCtihLiK3W31DkGj191hFY"
    suspend fun getCoordinator(address: String): Coordinates =
        webService.getdataFromAddress(address = address, apiKey = ap)

    suspend fun getVet(id: String): DocumentSnapshot {
        return firebaseFirestore.collection("Consultorios").document(id).get().await()
    }

    suspend fun registerDate(
        day: String,
        time: String,
        patient: String,
        reason: String,
        idVet: String
    ): Boolean {

        val appointmentDate =
            firebaseFirestore.collection("Citas").whereIn("status", listOf("earring", "confirmed"))
                .whereEqualTo("patient", patient).get().await()

        val date = hashMapOf(
            "day" to day,
            "time" to time,
            "patient" to patient,
            "reason" to reason,
            "status" to "earring",
            "idVeterinaria" to idVet
        )
        return if (appointmentDate.isEmpty) {
            firebaseFirestore.collection("Citas").document().set(date).await()
            true
        } else {
            false
        }
    }
}