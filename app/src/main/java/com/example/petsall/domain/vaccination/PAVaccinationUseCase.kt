package com.example.petsall.domain.vaccination

import com.example.petsall.utils.Resource
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAVaccinationUseCase @Inject constructor(private val repo: PAVaccinationRepo) {

    suspend operator fun invoke(idUser:String,idPet:String): Flow<Resource<List<DocumentSnapshot?>>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repo.getVaccinationList(idUser = idUser, idPet = idPet)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}