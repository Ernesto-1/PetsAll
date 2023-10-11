package com.example.petsall.presentation.files

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.petsall.data.remote.model.FilesDataClass

data class PAFilesState(
    val loading: Boolean? = false,
    val error: Boolean = false,
    var message: String = "",
    var success: Boolean = false,
    var data: List<FilesDataClass?> = listOf(),
    val dataFileSelected: MutableState<FilesDataClass> = mutableStateOf(FilesDataClass())

)
