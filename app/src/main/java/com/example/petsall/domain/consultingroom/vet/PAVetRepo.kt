package com.example.petsall.domain.consultingroom.vet

import android.location.Location
import com.google.firebase.firestore.DocumentSnapshot

interface PAVetRepo {

    suspend fun getVet(userLocate: Location):List<DocumentSnapshot?>
}