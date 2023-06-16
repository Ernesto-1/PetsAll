package com.example.petsall.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.signup.PASignUpUseCase
import com.example.petsall.utils.Resource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PASignUpViewModel @Inject constructor(private val useCase: PASignUpUseCase) : ViewModel() {

    var state by mutableStateOf(PASignUpState())
        private set

    fun onEvent(event: PASignUpEvent) {

        when (event) {
            is PASignUpEvent.Register -> {
                viewModelScope.launch {
                    useCase.invoke(event.email, event.password).collect { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                useCase.registerUser(email = event.email, name = event.name, lastname = event.lastname).collect() { registerUser ->
                                    when(registerUser){
                                        is Resource.Success -> {

                                        }
                                        else -> {}
                                    }
                                }
                                state = state.copy(
                                    success = true
                                )
                            }
                            is Resource.Failure -> {
                                state =
                                    if (result.exception.message?.contains("is already in use by another account") == true) {
                                        state.copy(
                                            message = "La dirección de correo electrónico ya está en uso por otra cuenta.",
                                        )
                                    } else if (result.exception.message?.contains("The email address is badly formatted") == true) {
                                        state.copy(
                                            message = "No es una direccion de correo electronico valida",
                                        )
                                    }else if(result.exception.message?.contains("Password should be at least 6 characters") == true){
                                        state.copy(
                                            message = "La contraseña debe tener al menos 6 caracteres",
                                        )
                                    }else{
                                        state.copy(
                                            message = "Error",
                                        )
                                    }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

}