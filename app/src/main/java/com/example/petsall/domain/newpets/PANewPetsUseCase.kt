package com.example.petsall.domain.newpets

import com.example.petsall.utils.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PANewPetsUseCase @Inject constructor(private val repository: PANewPetsRepo) {

    suspend fun registerPet(name: String, breed: String,birthday: String,pet: String) =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(repository.setNewPet(name = name,breed = breed,birthday = birthday, pet = pet)))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}