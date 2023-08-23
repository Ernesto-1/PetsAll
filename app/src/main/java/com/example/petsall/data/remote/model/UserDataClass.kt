package com.example.petsall.data.remote.model

import com.google.firebase.firestore.DocumentSnapshot

data class UserDataClass(
    val name: String = "",
    val lastname: String = "",
    val email: String = ""
)

fun DocumentSnapshot?.mapTouserDataClass(): UserDataClass? {
    if (this == null || !exists()) {
        return null
    }

    val username = getString("Nombre") ?: ""
    val lastname = getString("Apellidos") ?: ""
    val email = getString("Correo") ?: ""

    return UserDataClass(username, lastname,email)
}
