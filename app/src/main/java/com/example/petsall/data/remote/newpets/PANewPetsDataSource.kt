package com.example.petsall.data.remote.newpets

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PANewPetsDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firebaseFirestore: FirebaseFirestore) {

    suspend fun newPetRegister(name:String, breed: String, birthday: String, pets: String){
        val user = hashMapOf(
            "Mascota" to pets,
            "Nombre" to name,
            "Raza" to breed,
            "Fecha_Nacimiento" to birthday
        )
        firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").add(user).await()
    }
}