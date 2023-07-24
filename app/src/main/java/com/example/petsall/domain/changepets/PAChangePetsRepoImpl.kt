package com.example.petsall.domain.changepets

import com.example.petsall.data.remote.changepets.PAChangePetsDataSource
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAChangePetsRepoImpl @Inject constructor(private val datasource: PAChangePetsDataSource) : PAChangePetsRepo {
    override suspend fun getDataPets(selelectedPet: String): List<*>? = datasource.getDataPets(selelectedPet = selelectedPet)
}