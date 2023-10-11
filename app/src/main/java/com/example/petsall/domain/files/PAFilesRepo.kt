package com.example.petsall.domain.files

import com.google.firebase.firestore.DocumentSnapshot

interface PAFilesRepo {

    suspend fun getFilesList(idPet : String): List<DocumentSnapshot?>

}