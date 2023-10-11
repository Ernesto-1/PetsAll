package com.example.petsall.data.remote.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class ListFilesDataClass(
    val files: MutableList<FilesDataClass> = mutableListOf()
)

data class FilesDataClass(
    val pLicense: String = "",
    val reason: String = "",
    val comments: String = "",
    val diagnosis: String = "",
    val treatment: String = "",
    val date: Timestamp? = null,
    val medicine : ArrayList<*>? = null,
    val vaccines : ArrayList<*>? = null
)

fun DocumentSnapshot?.mapToFileData(): FilesDataClass? {
    if (this == null || !exists()) {
        return null
    }
    val pLicense = getString("CedulaP") ?: ""
    val reason = getString("Asunto") ?: ""
    val comments = getString("Comentarios") ?: ""
    val diagnosis = getString("Diagnostico") ?: ""
    val treatment = getString("Tratamiento") ?: ""
    val date = getTimestamp("Fecha")
    val medicine: ArrayList<*>? = get("Medicamentos") as? ArrayList<*>
    val vaccines: ArrayList<*>? = get("Vacunas") as? ArrayList<*>

    return FilesDataClass(pLicense = pLicense, reason = reason, comments = comments,diagnosis = diagnosis,treatment = treatment, date = date,medicine = medicine,vaccines = vaccines)
}


fun List<DocumentSnapshot?>.mapToListFilesDataClass(): ListFilesDataClass {
    val filesDataList = mapNotNull { documentSnapshot ->
        documentSnapshot.mapToFileData()
    }
    return ListFilesDataClass(filesDataList.toMutableList())
}
