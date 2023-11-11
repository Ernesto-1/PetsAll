package com.example.petsall.presentation.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.loggin.PALogginUseCase
import com.example.petsall.utils.Resource
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PALoginViewModel @Inject constructor(private val useCase: PALogginUseCase) : ViewModel() {

    private val auth = Firebase.auth

    var state by mutableStateOf(PALoginState())
        private set

    fun onEvent(event: PALoginEvent) {

        when (event) {
            is PALoginEvent.Login -> {
                viewModelScope.launch {
                    useCase.invoke(event.email, event.password).collect { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(
                                    success = true
                                )
                            }
                            is Resource.Failure -> {
                                state =
                                    if (result.exception.message?.contains("The password is invalid or the user does not have a password") == true || result.exception.message?.contains("There is no user record corresponding to this identifier") == true  ) {
                                        state.copy(
                                            message = "El usuario o contraseña son incorrectos",
                                        )
                                    } else if (result.exception.message?.contains("There is no user record corresponding to this identifier") == true) {
                                        state.copy(message = "No existe una cuenta asociada a este correo electronico")
                                    }else{
                                        state.copy(message = "Error")
                                    }
                                state = state.copy(success = false)

                            }
                            else -> {}
                        }
                    }
                }
            }
            is PALoginEvent.SignUp -> {

            }
        }
    }

    fun signInWithGoogle(credential: AuthCredential, home: () -> Unit) = viewModelScope.launch {
        try {
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                }
            }.addOnFailureListener { error ->

            }
        } catch (e: Exception) {
            Log.d("TAG_LOGIN", e.toString())
        }
    }

}