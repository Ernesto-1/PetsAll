package com.example.petsall.presentation.bussiness.business

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.data.remote.model.BusinessData
import com.example.petsall.domain.business.PABusinessUseCase
import com.example.petsall.utils.Resource
import com.example.petsall.utils.getListBussiness
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PABusinessListViewModel @Inject constructor(private val useCase: PABusinessUseCase) : ViewModel() {
    var state by mutableStateOf(PABusinessListState())
        private set


    fun onEvent(event: PABusinessListEvent) {
        when (event) {
            is PABusinessListEvent.GetDataBusiness -> {
                viewModelScope.launch {
                    useCase.invoke(userLocate = event.location).collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                val locationValue = Location("location value.")
                                locationValue.latitude = event.location.latitude
                                locationValue.longitude = event.location.longitude
                                val filterCoordinates = result.data
                                    .sortedBy {
                                        event.location.distanceTo(locationValue)
                                    }
                                state = state.copy(
                                    dataBusiness = filterCoordinates,
                                    listCategorys = getListBussiness(filterCoordinates)
                                )
                            }
                            else -> {}
                        }
                    }
                }

            }
            is PABusinessListEvent.FilterBusiness ->{
                filterPets(categorySelected = event.categoryselected, allBussiness = state.dataBusiness)
            }
            else -> {}
        }
    }
    private fun filterPets(categorySelected: List<String>, allBussiness: List<BusinessData>) {
        val filteredVets = if (categorySelected.isNotEmpty()) {
            state.dataBusiness.filter { bussiness ->
                val matchesPet = categorySelected.isEmpty() || bussiness.category ?.any { it in categorySelected } == true
                matchesPet
            }
        } else {
            allBussiness
        }
        state = state.copy(dataBusinessFilterNew = filteredVets)
    }

}