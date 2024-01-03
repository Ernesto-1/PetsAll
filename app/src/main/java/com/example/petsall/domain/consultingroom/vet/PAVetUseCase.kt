package com.example.petsall.domain.consultingroom.vet

import android.location.Location
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.data.remote.model.mapToListVetDataClass
import com.example.petsall.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAVetUseCase @Inject constructor(private val repo: PAVetRepo)  {

    suspend operator fun invoke(userLocate: Location): Flow<Resource<List<VetData>>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshots = repo.getVet(userLocate)
                val vetsData = documentSnapshots.mapToListVetDataClass().vets
                emit(Resource.Success(vetsData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}