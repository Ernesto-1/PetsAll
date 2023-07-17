package com.example.petsall.domain.vetdetail

import com.example.petsall.data.remote.vetdetail.model.Coordinates
import com.example.petsall.utils.Resource
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
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

    suspend fun getVet(id: String): Flow<Resource<DocumentSnapshot?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.getVet(id = id)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }

    suspend fun registerDate(day: String, time: String, patient: String, reason: String,idVet:String): Flow<Resource<Boolean?>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(
                    Resource.Success(
                        repository.registerDate(
                            day = day,
                            time = time,
                            patient = patient,
                            reason = reason,
                            idVet = idVet
                        )
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}
