package com.example.petsall.data.remote.forgotten

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PAForgottenPasswordDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun forgottenPassword(email: String):Boolean{
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            true
        }catch (e: Exception){
            false
        }
    }

}