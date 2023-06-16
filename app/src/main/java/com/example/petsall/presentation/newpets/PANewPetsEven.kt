package com.example.petsall.presentation.newpets

sealed class PANewPetsEven{
    data class Register(val name: String, val breeds: String,val birthday: String,val pets: String) : PANewPetsEven()
}
