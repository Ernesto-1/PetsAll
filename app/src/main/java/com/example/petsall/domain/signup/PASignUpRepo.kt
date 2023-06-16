package com.example.petsall.domain.signup

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

interface PASignUpRepo {
    suspend fun singUp(email: String, password: String): FirebaseUser?
    suspend fun signUpRegisterUser(email: String, name: String, lastname: String)
}