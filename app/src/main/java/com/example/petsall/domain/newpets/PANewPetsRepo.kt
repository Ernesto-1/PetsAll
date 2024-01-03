package com.example.petsall.domain.newpets

import android.graphics.Bitmap
import com.example.petsall.ui.newPet.RegisterPets
import com.google.firebase.Timestamp

interface PANewPetsRepo {

    suspend fun setNewPet(dataNew: RegisterPets): Boolean
}