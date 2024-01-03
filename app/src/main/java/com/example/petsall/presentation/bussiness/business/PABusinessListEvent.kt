package com.example.petsall.presentation.bussiness.business

import android.location.Location

sealed class PABusinessListEvent{
    data class GetDataBusiness(val location: Location) : PABusinessListEvent()
    data class FilterBusiness(val categoryselected: List<String> = listOf()) : PABusinessListEvent()

}
