package com.example.petsall.di

import com.example.petsall.domain.changepets.PAChangePetsRepo
import com.example.petsall.domain.changepets.PAChangePetsRepoImpl
import com.example.petsall.domain.home.PAHomeRepo
import com.example.petsall.domain.home.PAHomeRepoImpl
import com.example.petsall.domain.loggin.PALogginRepo
import com.example.petsall.domain.loggin.PALogginRepoImpl
import com.example.petsall.domain.newpets.PANewPetsRepo
import com.example.petsall.domain.newpets.PANewPetsRepoImpl
import com.example.petsall.domain.signup.PASignUpRepo
import com.example.petsall.domain.signup.PASignUpRepoImpl
import com.example.petsall.domain.vet.PAVetRepo
import com.example.petsall.domain.vet.PAVetRepoImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun providesLoginRepository(repoLogin: PALogginRepoImpl): PALogginRepo
    @Binds
    abstract fun providesSignUpRepository(repoSignUp: PASignUpRepoImpl): PASignUpRepo
    @Binds
    abstract fun providesHomeRepository(repoHome: PAHomeRepoImpl): PAHomeRepo
    @Binds
    abstract fun providesVetRepository(repoVet: PAVetRepoImpl): PAVetRepo
    @Binds
    abstract fun providesNewPetsRepository(repoNewPets: PANewPetsRepoImpl): PANewPetsRepo
    @Binds
    abstract fun providesChangePetsRepository(repoChangePets: PAChangePetsRepoImpl): PAChangePetsRepo

    companion object {
        @Provides
        @Singleton
        fun provideFirestore() = FirebaseAuth.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

        @Provides
        @Singleton
        fun provideFirebaseStorage() = FirebaseStorage.getInstance()


    }


}