package com.example.petsall.presentation.signup

sealed class PASignUpEvent {
    data class Register(val email: String, val password: String,val name: String,val lastname: String) : PASignUpEvent()

}
