package com.example.petsall.data.remote.newpets

import android.graphics.Bitmap
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class PANewPetsDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {

    suspend fun newPetRegister(
        name: String,
        breed: String,
        birthday: Timestamp?,
        pets: String,
        img: Bitmap?
    ): Boolean {
        try {
            val setPetRef = firebaseFirestore.collection("Users")
                .document(firebaseAuth.uid.toString())
                .collection("Mascotas").document()

            val updates = hashMapOf<String, Any>(
                "Mascota" to pets,
                "Nombre" to name,
                "Raza" to breed,
                "ImgUrl" to ""
            )
            if (birthday != null) {
                updates["Fecha_Nacimiento"] = birthday
            }

            if (img != null) {
                val baos = ByteArrayOutputStream()
                img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()

                val storageRef = storage.reference.child("images/${firebaseAuth.uid.toString()}/pets/${setPetRef.id}/${setPetRef.id}")
                val uploadTask = storageRef.putBytes(data).await()
                uploadTask.metadata?.reference?.downloadUrl?.await()?.let { downloadUri ->
                    updates["ImgUrl"] = downloadUri.toString()
                }
            }

            updates["id"] = setPetRef.id
            setPetRef.set(updates).await()

            return true
        } catch (e: Exception) {
            return false
        }
    }
}