package com.example.petsall.presentation.newpets

data class PANewPetsState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: Map<String, Any>? = mapOf()
)
