package com.example.petsall.domain.files

import android.util.Log
import com.example.petsall.data.remote.model.FilesDataClass
import com.example.petsall.data.remote.model.VaccineDataClass
import com.example.petsall.data.remote.model.mapToListFilesDataClass
import com.example.petsall.data.remote.model.mapToListVaccineDataClass
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
                val filesData = documentSnapshot.mapToListFilesDataClass().files
                emit(Resource.Success(filesData))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}