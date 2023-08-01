package com.example.petsall.domain.newpets

import android.graphics.Bitmap
import com.google.firebase.Timestamp

interface PANewPetsRepo {

    suspend fun setNewPet(name: String, breed : String, birthday: Timestamp?, pet : String, img: Bitmap?)
}