package com.example.petsall.domain.newpets

import android.graphics.Bitmap
import com.example.petsall.data.remote.newpets.PANewPetsDataSource
import com.google.firebase.Timestamp
import javax.inject.Inject

class PANewPetsRepoImpl @Inject constructor(private val datasource: PANewPetsDataSource):PANewPetsRepo {
    override suspend fun setNewPet(name: String, breed: String, birthday: Timestamp?, pet: String, img:Bitmap?): Boolean = datasource.newPetRegister(name = name, breed = breed, birthday = birthday, pets = pet, img = img)
}