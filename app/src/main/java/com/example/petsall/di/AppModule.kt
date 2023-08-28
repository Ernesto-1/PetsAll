package com.example.petsall.di

import android.content.Context
import com.example.petsall.domain.WebService
import com.example.petsall.domain.business.PABusinessRepo
import com.example.petsall.domain.business.PABusinessRepoImpl
import com.example.petsall.domain.changepets.PAChangePetsRepo
import com.example.petsall.domain.changepets.PAChangePetsRepoImpl
import com.example.petsall.domain.emergency.PAEmergencyRepo
import com.example.petsall.domain.emergency.PAEmergencyRepoImpl
import com.example.petsall.domain.home.PAHomeRepo
import com.example.petsall.domain.home.PAHomeRepoImpl
import com.example.petsall.domain.loggin.PALogginRepo
import com.example.petsall.domain.loggin.PALogginRepoImpl
import com.example.petsall.domain.newpets.PANewPetsRepo
import com.example.petsall.domain.newpets.PANewPetsRepoImpl
import com.example.petsall.domain.signup.PASignUpRepo
import com.example.petsall.domain.signup.PASignUpRepoImpl
import com.example.petsall.domain.vaccination.PAVaccinationRepo
import com.example.petsall.domain.vaccination.PAVaccinationRepoImpl
import com.example.petsall.domain.vet.PAVetRepo
import com.example.petsall.domain.vet.PAVetRepoImpl
import com.example.petsall.domain.vetdetail.PAVetDetailRepo
import com.example.petsall.domain.vetdetail.PAVetDetailRepoImpl
import com.example.petsall.utils.AppConstans
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
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
    @Binds
    abstract fun providesVetDetailsRepository(repoVetDetail: PAVetDetailRepoImpl): PAVetDetailRepo
    @Binds
    abstract fun providesEmergencyRepository(repoEmergency: PAEmergencyRepoImpl): PAEmergencyRepo
    @Binds
    abstract fun providesVaccinationRepository(repoVaccination: PAVaccinationRepoImpl): PAVaccinationRepo
    @Binds
    abstract fun providesBusinessRepository(repoBusiness:PABusinessRepoImpl): PABusinessRepo

    companion object {

        @Provides
        fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().also {
                        it.setLevel(
                            HttpLoggingInterceptor.Level.HEADERS
                        )
                    }
                )
                .build()

        @Provides
        fun providesRetrofitInstance(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        fun providesCurrencyService(retrofit: Retrofit) = retrofit.create<WebService>()

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