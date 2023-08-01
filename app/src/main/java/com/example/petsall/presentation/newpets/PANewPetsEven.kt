package com.example.petsall.presentation.newpets

import android.graphics.Bitmap
import com.google.firebase.Timestamp

sealed class PANewPetsEven{
    data class Register(val name: String, val breeds: String, val birthday: Timestamp?, val pets: String, val img:Bitmap?) : PANewPetsEven()
}
