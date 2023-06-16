package com.example.petsall.presentation.login

sealed class PALoginEvent {
    data class Login(val email: String, val password: String) : PALoginEvent()
    data class SignUp(val state: String) : PALoginEvent()


}
