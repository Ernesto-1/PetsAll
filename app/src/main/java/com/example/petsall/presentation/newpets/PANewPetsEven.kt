package com.example.petsall.presentation.newpets

import android.graphics.Bitmap

sealed class PANewPetsEven{
    data class Register(val name: String, val breeds: String,val birthday: String,val pets: String, val img:Bitmap?) : PANewPetsEven()
}
