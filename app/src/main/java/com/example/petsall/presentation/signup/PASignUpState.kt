package com.example.petsall.presentation.signup

data class PASignUpState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false
    )
