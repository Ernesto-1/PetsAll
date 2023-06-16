package com.example.petsall.domain.signup

import com.example.petsall.utils.Resource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PASignUpUseCase @Inject constructor(private val repository: PASignUpRepo) {

    suspend operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.singUp(email = email,password = password)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    suspend fun registerUser(email: String, name: String,lastname: String) =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.signUpRegisterUser(email = email, name = name, lastname = lastname)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}