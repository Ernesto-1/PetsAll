package com.example.petsall.domain.newpets

import android.graphics.Bitmap
import android.util.Log
import com.example.petsall.utils.Resource
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PANewPetsUseCase @Inject constructor(private val repository: PANewPetsRepo) {

    suspend fun registerPet(name: String, breed: String, birthday: Timestamp?, pet: String, img:Bitmap?): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.setNewPet(name = name,breed = breed,birthday = birthday, pet = pet, img = img)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}