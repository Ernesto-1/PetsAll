package com.example.petsall.domain

import com.example.petsall.data.remote.consultingroom.vetdetail.model.Coordinates
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("geocode/json")
    suspend fun getdataFromAddress(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Coordinates
}