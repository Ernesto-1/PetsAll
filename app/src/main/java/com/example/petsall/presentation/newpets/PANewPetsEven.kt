package com.example.petsall.presentation.newpets

import android.graphics.Bitmap
import com.example.petsall.ui.newPet.RegisterPets
import com.google.firebase.Timestamp

sealed class PANewPetsEven{
    data class Register(val dataNew: RegisterPets) : PANewPetsEven()
}

