package com.example.petsall.domain.newpets

import android.graphics.Bitmap
import android.util.Log
import com.example.petsall.ui.newPet.RegisterPets
import com.example.petsall.utils.Resource
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PANewPetsUseCase @Inject constructor(private val repository: PANewPetsRepo) {

    suspend fun registerPet(dataRegister: RegisterPets): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.setNewPet(dataRegister)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}