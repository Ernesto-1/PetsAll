package com.example.petsall.presentation.login

data class PALoginState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    val message: String = "",
    var success: Boolean = false

)