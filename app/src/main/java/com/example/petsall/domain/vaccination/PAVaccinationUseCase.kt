package com.example.petsall.domain.vaccination

import android.util.Log
import com.example.petsall.data.remote.model.VaccineDataClass
import com.example.petsall.data.remote.model.mapToListVaccineDataClass
import com.example.petsall.data.remote.model.mapTouserDataClass
import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAVaccinationUseCase @Inject constructor(private val repo: PAVaccinationRepo) {

    suspend operator fun invoke(idUser:String,idPet:String): Flow<Resource<List<VaccineDataClass?>>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshot = repo.getVaccinationList(idUser = idUser, idPet = idPet)
                val vaccineData = documentSnapshot.mapToListVaccineDataClass().vaccines
                emit(Resource.Success(vaccineData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}