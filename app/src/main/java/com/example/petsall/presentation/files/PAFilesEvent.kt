package com.example.petsall.presentation.files

sealed class PAFilesEvent {
    data class GetFiles(val idPet: String) : PAFilesEvent()
}
