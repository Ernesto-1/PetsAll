package com.example.petsall.domain.signup

import com.example.petsall.data.remote.signup.PASignUpDataSource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class PASignUpRepoImpl @Inject constructor(private val dataSource: PASignUpDataSource) : PASignUpRepo {
    override suspend fun singUp(email: String, password: String): FirebaseUser? = dataSource.singUp(email = email, password = password)
    override suspend fun signUpRegisterUser(email: String, name: String, lastname: String) = dataSource.singUpRegisterUser(email = email,name = name, lastname = lastname)
}