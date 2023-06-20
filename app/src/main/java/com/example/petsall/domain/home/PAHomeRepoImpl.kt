package com.example.petsall.domain.home

import com.example.petsall.data.remote.home.PAHomeDatasource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class PAHomeRepoImpl @Inject constructor(private val datasource: PAHomeDatasource): PAHomeRepo {
    override suspend fun getDataUser(): DocumentSnapshot? = datasource.getDataUser()
    override suspend fun getDataPets(): List<DocumentSnapshot?> = datasource.getDataPets()
}