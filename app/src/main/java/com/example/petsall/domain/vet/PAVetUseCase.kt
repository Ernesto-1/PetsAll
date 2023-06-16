package com.example.petsall.domain.vet

import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAVetUseCase @Inject constructor(private val repo: PAVetRepo)  {

    suspend operator fun invoke(): Flow<Resource<List<DocumentSnapshot?>>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repo.getVet()))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}