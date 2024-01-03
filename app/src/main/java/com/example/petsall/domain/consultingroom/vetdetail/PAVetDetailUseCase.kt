package com.example.petsall.domain.consultingroom.vetdetail

import android.util.Log
import com.example.petsall.data.remote.model.PetData
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.data.remote.model.mapToPetsDataClass
import com.example.petsall.data.remote.model.mapToVetData
import com.example.petsall.data.remote.consultingroom.vetdetail.model.Coordinates
import com.example.petsall.presentation.model.RegisterDataRequest
import com.example.petsall.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAVetDetailUseCase @Inject constructor(private val repository: PAVetDetailRepo) {

    suspend operator fun invoke(address: String): Flow<Resource<Coordinates>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getDataFromAddress(address = address)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    suspend fun getVet(id: String): Flow<Resource<VetData?>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshot = repository.getVet(id = id)
                val vetData = documentSnapshot.mapToVetData()
                emit(Resource.Success(vetData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    suspend fun registerDate(data: RegisterDataRequest): Flow<Resource<Boolean?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        repository.registerDate(
                            data = data
                        )
                    )
                )
            } catch (e: Exception) {
                Log.d("DetailDate", "registerDate: ${e.message}")
                emit(Resource.Failure(e))
            }
        }

    suspend fun getPets(): Flow<Resource<List<PetData>>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshots = repository.getDataPets()
                val petsData = documentSnapshots.mapToPetsDataClass().mascotas
                emit(Resource.Success(petsData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}
