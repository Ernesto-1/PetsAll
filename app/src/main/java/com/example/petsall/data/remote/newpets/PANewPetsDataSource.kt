package com.example.petsall.data.remote.newpets

import android.graphics.Bitmap
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class PANewPetsDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firebaseFirestore: FirebaseFirestore,private val storage: FirebaseStorage) {

    suspend fun newPetRegister(name:String, breed: String, birthday: Timestamp?, pets: String, img:Bitmap?){
        val pet = hashMapOf(
            "Mascota" to pets,
            "Nombre" to name,
            "Raza" to breed,
            "Fecha_Nacimiento" to birthday,
            "ImgUrl" to ""
        )

        if (img != null){
            val baos = ByteArrayOutputStream()
            img.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            val setPet = firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").add(pet).await()
            val setImg = storage.reference.child("images/${firebaseAuth.uid.toString()}/pets/${setPet.id}/${setPet.id}")
            setImg.putBytes(data).await()
            val downloadUri = setImg.downloadUrl.await()
            firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").document(setPet.id).update("ImgUrl",downloadUri.toString())
        }else{
           firebaseFirestore.collection("Users").document(firebaseAuth.uid.toString()).collection("Mascotas").add(pet).await()
        }

    }


}