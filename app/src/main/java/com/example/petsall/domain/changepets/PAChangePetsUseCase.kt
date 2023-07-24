package com.example.petsall.domain.changepets

import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAChangePetsUseCase @Inject constructor(private val repository: PAChangePetsRepo) {

    suspend fun getPets(selelectedPet:String): Flow<Resource<List<*>?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getDataPets(selelectedPet = selelectedPet)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}