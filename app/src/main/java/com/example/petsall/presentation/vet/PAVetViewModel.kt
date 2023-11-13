package com.example.petsall.presentation.vet

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petsall.data.remote.model.VetData
import com.example.petsall.domain.vet.PAVetUseCase
import com.example.petsall.utils.Resource
import com.example.petsall.utils.getList
import com.example.petsall.utils.getListSpecialties
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PAVetViewModel @Inject constructor(private val useCase: PAVetUseCase) : ViewModel() {

    var state by mutableStateOf(PAVetState())
        private set

    fun onEvent(event: PAVetEvent) {
        when (event) {
            is PAVetEvent.GetDataVet -> {
                viewModelScope.launch {
                    useCase.invoke().collect() { result ->
                        when (result) {
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                val locationValue = Location("location value.")
                                if (event.location != null) {
                                    val filterCoordinates = result.data.filter {
                                        locationValue.latitude = it.lat
                                        locationValue.longitude = it.long
                                        event.location.distanceTo(locationValue) < 10000
                                    }.sortedBy {
                                        locationValue.latitude = it.lat
                                        locationValue.longitude = it.long
                                        event.location.distanceTo(locationValue)
                                    }

                                    state = state.copy(
                                        dataVet = filterCoordinates,
                                        listSector = getList(filterCoordinates),
                                        listSpecialties = getListSpecialties(filterCoordinates)
                                    )
                                } else {
                                    state = state.copy(dataVet = listOf())
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            is PAVetEvent.FilterVets -> {
                filterPets(event.listSelectPet,event.listSelectSpecialties,state.dataVet)
            }
        }
    }
    private fun filterPets(listSelectPet: List<String>, listSelectSpecialties: List<String>, allVets: List<VetData>) {
        val filteredVets = if (listSelectPet.isNotEmpty() || listSelectSpecialties.isNotEmpty()) {
            state.dataVet.filter { vet ->
                val matchesPet = listSelectPet.isEmpty() || vet.listSpecializedSector?.any { it in listSelectPet } == true
                val matchesSpecialties = listSelectSpecialties.isEmpty() || vet.listSpecialties?.any { it in listSelectSpecialties } == true
                matchesPet && matchesSpecialties
            }
        } else {
            allVets
        }

        state = state.copy(dataVetFilterNew = filteredVets)
    }
}