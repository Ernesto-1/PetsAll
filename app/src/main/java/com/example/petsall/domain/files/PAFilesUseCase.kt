package com.example.petsall.domain.files

import com.example.petsall.data.remote.model.*
import com.example.petsall.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PAFilesUseCase @Inject constructor(private val repo: PAFilesRepo) {

    suspend operator fun invoke(idPet: String): Flow<Resource<List<FilesDataClass?>>> =
        flow {
            emit(Resource.Loading())
            try {
                val documentSnapshot = repo.getFilesList(idPet = idPet)
                val filesData = documentSnapshot.toDataMedicalReport().record
                emit(Resource.Success(filesData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}