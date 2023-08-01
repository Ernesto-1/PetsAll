package com.example.petsall.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BottomMenuViewModel : ViewModel() {
    private val _selectedOption = mutableStateOf("Inicio")
    val selectedOption: State<String> get() = _selectedOption

    fun setSelectedOption(option: String) {
        _selectedOption.value = option
    }
}