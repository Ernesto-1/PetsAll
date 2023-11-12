package com.example.petsall.data.remote.vetdetail

import android.util.Log
import com.example.petsall.Constants.STATUS_CONFIRMED
import com.example.petsall.Constants.STATUS_PENDING
import com.example.petsall.Constants.STATUS_PROPOSAL
import com.example.petsall.data.remote.vetdetail.model.Coordinates
import com.example.petsall.domain.WebService
import com.example.petsall.presentation.model.RegisterDataRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAVetDetailDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
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
        data: RegisterDataRequest
    ): Boolean {
        try {
            val existingAppointments = firebaseFirestore.collection("Citas")
                .whereIn("status", listOf(STATUS_PENDING, STATUS_CONFIRMED, STATUS_PROPOSAL))
                .whereEqualTo("idPatient", data.idPatient)
                .get()
                .await()
            Log.e("registerDate", existingAppointments.isEmpty.toString())

            return if (existingAppointments.documents.isEmpty()) {
                val newAppointment = hashMapOf(
                    "citaSolicitada" to data.requestedAppointment,
                    "idPatient" to data.idPatient,
                    "motivo" to data.reason,
                    "patient" to data.patient,
                    "status" to "pendiente",
                    "idConsultorio" to data.idConsult,
                    "latitud" to data.lat,
                    "longitud" to data.long,
                    "mascota" to data.pet,
                    "nombreConsultorio" to data.nameConsult,
                    "userId" to firebaseAuth.uid.toString(),
                    "edad" to data.age,
                    "asunto" to "Consulta")
                firebaseFirestore.collection("Citas").add(newAppointment).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("registerDate", "Error adding appointment", e)
            return false
        }
    }


    suspend fun getDataPets(): List<DocumentSnapshot?> {
        val dataPets = firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString())
            .collection("Mascotas").get().await()
        return dataPets.documents
    }
}