package com.example.petsall.domain.loggin

import com.google.firebase.auth.FirebaseUser

interface PALogginRepo {
    suspend fun singIn(email: String, password: String): FirebaseUser?
}