package com.example.petsall.domain.forgotten

interface PAForgottenPasswordRepo {

    suspend fun forgottenPassword(email: String): Boolean
}