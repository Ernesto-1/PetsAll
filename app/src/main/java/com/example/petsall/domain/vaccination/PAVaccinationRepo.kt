package com.example.petsall.domain.vaccination

import com.google.firebase.firestore.DocumentSnapshot

interface PAVaccinationRepo {

    suspend fun getVaccinationList(idUser: String, idPet : String): List<DocumentSnapshot?>
}