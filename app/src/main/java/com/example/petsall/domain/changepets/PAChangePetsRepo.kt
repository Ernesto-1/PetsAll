package com.example.petsall.domain.changepets


interface PAChangePetsRepo {
    suspend fun getDataPets(selelectedPet : String): List<*>?
}