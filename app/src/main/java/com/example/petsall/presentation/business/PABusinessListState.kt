package com.example.petsall.presentation.business

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.petsall.data.remote.model.BusinessData

data class PABusinessListState(
    val loadingPets: Boolean = true,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var dataBusiness: List<BusinessData> = listOf(),
    val dataBusinesSelected: MutableState<BusinessData> = mutableStateOf(BusinessData())

    )
