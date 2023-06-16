package com.example.petsall.domain.newpets

import com.example.petsall.data.remote.newpets.PANewPetsDataSource
import javax.inject.Inject

class PANewPetsRepoImpl @Inject constructor(private val datasource: PANewPetsDataSource):PANewPetsRepo {
    override suspend fun setNewPet(name: String, breed: String, birthday: String, pet: String) = datasource.newPetRegister(name = name, breed = breed, birthday = birthday, pets = pet)
}