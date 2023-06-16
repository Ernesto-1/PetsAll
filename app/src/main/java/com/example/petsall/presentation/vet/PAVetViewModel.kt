package com.example.petsall.presentation.vet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.vet.PAVetUseCase
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAVetViewModel @Inject constructor(private val useCase: PAVetUseCase): ViewModel() {

    var state by mutableStateOf(PAVetState())
        private set

    fun onEvent(event: PAVetEvent) {
        when (event) {
            is PAVetEvent.GetDataUser -> {
                viewModelScope.launch {
                    useCase.invoke().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(data = result.data)
                            }
                            else -> {}
                        }
                    }
                }

            }
            else -> {}
        }
    }
}