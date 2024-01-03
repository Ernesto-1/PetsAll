package com.example.petsall.data.remote.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class ListFilesDataClass(
    val files: MutableList<FilesDataClass> = mutableListOf()
)

data class FilesDataClass(
    val id: String = "",
    val medicalMatter: String = "",
    val license: String = "",
    val comments: String = "",
    val diagnosis: String = "",
    val date: Timestamp? = null,
    val treatment: List<*>? = null,
    val medicalRecordList: List<Medicamento> = arrayListOf()
)

fun List<DocumentSnapshot?>.toDataMedicalReport(): MedicalRecordData {
    val data = mapNotNull { documentSnapshot ->
        documentSnapshot.mapToMedicalRecordData()
    }
    return MedicalRecordData(
        record = data
    )
}

fun DocumentSnapshot?.mapToMedicalRecordData(): FilesDataClass {
    if (this == null || !exists()) {
        return FilesDataClass()
    }

    val idRecord = this.reference.id
    val medicalMatter = getString("Asunto") ?: ""
    val licence = getString("CedulaP") ?: ""
    val comments = getString("Comentarios") ?: ""
    val diagnosis = getString("Diagnostico") ?: ""
    val date = getTimestamp("Fecha")
    val treatment: ArrayList<*>? = get("Tratamiento") as? ArrayList<*>
    val vaccines: ArrayList<*>? = get("Cartilla") as? ArrayList<*>
    val medicalRecordList: ArrayList<Medicamento> = arrayListOf()

    vaccines?.forEach { vacc ->
        val hash = vacc as HashMap<*, *>
        medicalRecordList.add(
            Medicamento(
                nombre = hash["treatment"].toString(),
                numero_medicamento = hash["code"].toString(),
                tipo = hash["type"].toString(),
                nextAplication = hash["nextAplication"].toString()
            )
        )
    }

    return FilesDataClass(
        idRecord, medicalMatter, licence, comments, diagnosis, date, treatment, medicalRecordList
    )
}
data class MedicalRecordData(
    val record: List<FilesDataClass> = listOf()
)
data class Medicamento(
    val nombre: String = "",
    val numero_medicamento: String = "",
    val tipo: String = "",
    val nextAplication: String = ""
)

