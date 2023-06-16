package com.example.petsall.domain.loggin

import com.example.petsall.data.remote.login.PALogginDataSource
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class PALogginRepoImpl @Inject constructor(private val dataSource: PALogginDataSource): PALogginRepo{
    override suspend fun singIn(email: String, password: String): FirebaseUser? = dataSource.logIn(email = email, password = password)
}