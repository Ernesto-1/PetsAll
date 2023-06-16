package com.example.petsall.domain.newpets

interface PANewPetsRepo {

    suspend fun setNewPet(name: String, breed : String,birthday: String,pet : String)
}