package com.example.petsall.domain.newpets

import android.graphics.Bitmap

interface PANewPetsRepo {

    suspend fun setNewPet(name: String, breed : String,birthday: String,pet : String,img: Bitmap?)
}