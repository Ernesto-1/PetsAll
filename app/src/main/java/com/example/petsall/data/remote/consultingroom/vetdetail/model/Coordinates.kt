package com.example.petsall.data.remote.consultingroom.vetdetail.model

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @SerializedName("results" ) val results: List<GeocodingResult> = listOf(),
    @SerializedName("status"  ) var status  : String? = null
)

data class GeocodingResult (
    @SerializedName("geometry" ) var geometry : Geometry? = Geometry(),
    @SerializedName("place_id" ) var placeId : String? = null,
)

data class Geometry (
    @SerializedName("location") var location : Location? = Location(),
)

data class Location (
    @SerializedName("lat" ) var lat : Double? = null,
    @SerializedName("lng" ) var lng : Double? = null

)