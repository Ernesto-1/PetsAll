package com.example.petsall.presentation.forgotten

sealed class PAForgottenPasswordEvent{
    data class ForgottenPassword(val email: String) : PAForgottenPasswordEvent()
}
