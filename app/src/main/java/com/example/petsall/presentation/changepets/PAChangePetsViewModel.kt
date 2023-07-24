package com.example.petsall.presentation.changepets

import android.util.Log
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

        fun onEvent(event: PAChangePetsEvent) {
        when (event) {
            is PAChangePetsEvent.GetDataPets -> {
                viewModelScope.launch {
                    useCase.getPets(event.pet).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy(dataPets = result.data)
                                searchQuery("")
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
    fun searchQuery(query: String){
        if (query.isNotEmpty()){
            state = state.copy(dataPetsSearch = state.dataPets?.filter { it.toString().contains(query,ignoreCase = true)})
        }else{
            state = state.copy(dataPetsSearch = state.dataPets)
        }
    }
}