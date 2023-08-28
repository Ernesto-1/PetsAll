package com.example.petsall.domain.vet

import android.util.Log
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.data.remote.model.mapToListVetDataClass
import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAVetUseCase @Inject constructor(private val repo: PAVetRepo)  {

    suspend operator fun invoke(): Flow<Resource<List<VetData>>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshots = repo.getVet()
                val vetsData = documentSnapshots.mapToListVetDataClass().vets
                emit(Resource.Success(vetsData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}