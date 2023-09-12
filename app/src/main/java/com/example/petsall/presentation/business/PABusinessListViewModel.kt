package com.example.petsall.presentation.business

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.domain.business.PABusinessUseCase
import com.example.petsall.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PABusinessListViewModel @Inject constructor(private val useCase: PABusinessUseCase) : ViewModel() {
    var state by mutableStateOf(PABusinessListState())
        private set

    init {
        onEvent(PABusinessListEvent.GetDataBusiness(""))
    }

    fun onEvent(event: PABusinessListEvent) {
        when (event) {
            is PABusinessListEvent.GetDataBusiness -> {
                viewModelScope.launch {
                    useCase.invoke(nameListBusiness = event.nameListBusiness).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                Log.d("fgcvhbjknlm", result.data.toString())
                                state = state.copy(dataBusiness = result.data)
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