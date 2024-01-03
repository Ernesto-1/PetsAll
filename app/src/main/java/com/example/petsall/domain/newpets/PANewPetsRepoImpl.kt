package com.example.petsall.domain.newpets

import android.graphics.Bitmap
import com.example.petsall.data.remote.newpets.PANewPetsDataSource
import com.example.petsall.ui.newPet.RegisterPets
import com.google.firebase.Timestamp
import javax.inject.Inject

class PANewPetsRepoImpl @Inject constructor(private val datasource: PANewPetsDataSource):PANewPetsRepo {
    override suspend fun setNewPet(dataNew: RegisterPets): Boolean = datasource.newPetRegister(dataNew)
}