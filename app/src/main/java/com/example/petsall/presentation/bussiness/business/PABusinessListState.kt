package com.example.petsall.presentation.bussiness.business

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.petsall.data.remote.model.BusinessData

data class PABusinessListState(
    val loadingPets: Boolean = true,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var listCategorys: List<String> = listOf(),
    val categorySelected: MutableState<List<String>> = mutableStateOf(listOf()),
    val categorySelectedTemp: MutableState<List<String>> = mutableStateOf(listOf()),
    var dataBusiness: List<BusinessData> = listOf(),
    var dataBusinessFilterNew:  List<BusinessData> = listOf(),
    val dataBusinesSelected: MutableState<BusinessData> = mutableStateOf(BusinessData())

    )
