package com.example.petsall.domain.home

import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAHomeUseCase @Inject constructor(private val repository: PAHomeRepo) {

    suspend operator fun invoke(): Flow<Resource<DocumentSnapshot?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getDataUser()))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    suspend fun getPets(): Flow<Resource<QuerySnapshot?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getDataPets()))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}