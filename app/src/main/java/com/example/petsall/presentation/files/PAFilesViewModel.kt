package com.example.petsall.presentation.files

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.files.PAFilesUseCase
import com.example.petsall.presentation.vaccination.PAVaccinationState
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAFilesViewModel @Inject constructor(private val useCase: PAFilesUseCase) : ViewModel() {

    var state by mutableStateOf(PAFilesState())
        private set

    fun onEvent(event: PAFilesEvent) {
        when (event) {
            is PAFilesEvent.GetFiles -> {
                viewModelScope.launch {
                    useCase.invoke(idPet = event.idPet).collect() { result ->
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