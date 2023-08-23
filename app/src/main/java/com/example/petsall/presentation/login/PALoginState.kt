package com.example.petsall.presentation.login

data class PALoginState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false

)