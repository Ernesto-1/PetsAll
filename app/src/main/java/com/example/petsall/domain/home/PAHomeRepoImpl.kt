package com.example.petsall.domain.home

import com.example.petsall.data.remote.home.PAHomeDatasource
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class PAHomeRepoImpl @Inject constructor(private val datasource: PAHomeDatasource): PAHomeRepo {
    override suspend fun getDataUser(): DocumentSnapshot? = datasource.getDataUser()
    override suspend fun getDataPets(): List<DocumentSnapshot?> = datasource.getDataPets()
    override suspend fun getDatePet(): List<DocumentSnapshot?> = datasource.getDatePet()
    override suspend fun deleteDataPet(idPet: String): Boolean = datasource.deleteDataPet(idPet = idPet)

}