package com.example.petsall.presentation.newpets

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.newpets.PANewPetsUseCase
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PANewPetsViewModel @Inject constructor(private val useCase: PANewPetsUseCase) : ViewModel() {

    var state by mutableStateOf(PANewPetsState())
        private set

    fun onEvent(event: PANewPetsEven) {
        when (event) {
            is PANewPetsEven.Register -> {
                viewModelScope.launch {
                    useCase.registerPet(name = event.name, breed = event.breeds, birthday = event.birthday, pet = event.pets, img = event.img).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                state = state.copy()
                                Log.d("rtyubnm", result.data.toString())
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