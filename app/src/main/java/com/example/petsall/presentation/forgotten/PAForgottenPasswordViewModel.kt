package com.example.petsall.presentation.forgotten

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.forgotten.PAForgottenPasswordUseCase
import com.example.petsall.presentation.home.PAHomeEvent
import com.example.petsall.presentation.home.PAHomeState
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAForgottenPasswordViewModel @Inject constructor(private val useCase: PAForgottenPasswordUseCase): ViewModel() {

    var state by mutableStateOf(PAForgottenPasswordState())
        private set

    fun onEvent(event: PAForgottenPasswordEvent) {
        when (event) {
            is PAForgottenPasswordEvent.ForgottenPassword -> {
                viewModelScope.launch{
                    useCase.invoke(event.email).collect(){ result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(success = false, loading = true, isError = false)

                            }
                            is Resource.Failure -> {
                                state = state.copy(success = false, loading = null, isError = true)

                            }
                            is Resource.Success -> {
                                state = state.copy(success = result.data, loading = null, isError = false)

                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}