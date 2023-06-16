package com.example.petsall.data.remote.signup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PASignUpDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth,private val firebaseFirestore: FirebaseFirestore) {

    suspend fun singUp(email:String, password: String): FirebaseUser?{
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        firebaseAuth.currentUser?.sendEmailVerification()?.await()
        return authResult.user
    }
    suspend fun singUpRegisterUser(email:String, name: String,lastname: String){
        val user = hashMapOf(
            "Nombre" to name,
            "Apellidos" to lastname,
            "Correo" to email
        )
        firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).set(user).await()
    }

}