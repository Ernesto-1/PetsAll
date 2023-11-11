package com.example.petsall.domain.home

import android.util.Log
import com.example.petsall.data.remote.model.*
import com.example.petsall.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAHomeUseCase @Inject constructor(private val repository: PAHomeRepo) {

    suspend operator fun invoke(): Flow<Resource<UserDataClass?>> = flow {
        emit(Resource.Loading())
        try {
            val documentSnapshot = repository.getDataUser()
            val userData = documentSnapshot?.mapTouserDataClass()
            emit(Resource.Success(userData))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    suspend fun getPets(): Flow<Resource<List<PetData>?>> = flow {
            emit(Resource.Loading())
            try {
                val documentSnapshots = repository.getDataPets()
                val petsData = documentSnapshots.mapToPetsDataClass().mascotas
                emit(Resource.Success(petsData))
            } catch (e: Exception) {
                //emit(Resource.Failure(e))
            }
        }

    suspend fun getDatePet(): Flow<Resource<List<PetDateMedic>?>> = flow {
        emit(Resource.Loading())
        try {
            val documentSnapshots = repository.getDatePet()
            val petsDateMedic = documentSnapshots.mapToDateMedicDataClass().datesMedic
            emit(Resource.Success(petsDateMedic))
        } catch (e: Exception) {
            // emit(Resource.Failure(e))
        }
    }

    suspend fun deleteDatePet(idPet: String): Flow<Resource<Boolean?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.deleteDataPet(idPet = idPet)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    suspend fun updateStatusDate(idPet: String): Flow<Resource<Boolean?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.updateStatusDate(idPet = idPet)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    suspend fun deleteDateQuote(idPet: String): Flow<Resource<Boolean?>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repository.deletePetQuote(idPet = idPet)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}