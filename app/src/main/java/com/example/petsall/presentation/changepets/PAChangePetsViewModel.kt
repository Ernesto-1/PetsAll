package com.example.petsall.presentation.changepets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.changepets.PAChangePetsUseCase
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAChangePetsViewModel @Inject constructor(private val useCase: PAChangePetsUseCase) : ViewModel() {

    var state by mutableStateOf(PAChangePetsState())
        private set

    init {
    onEvent(PAChangePetsEvent.GetDataPets(""))
    }

        fun onEvent(event: PAChangePetsEvent) {
        when (event) {
            is PAChangePetsEvent.GetDataPets -> {
                viewModelScope.launch {
                    useCase.getPets().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(dataPets = result.data)
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}