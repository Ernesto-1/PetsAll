package com.example.petsall.presentation.business

sealed class PABusinessListEvent{
    data class GetDataBusiness(val state: String) : PABusinessListEvent()
}
