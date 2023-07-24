package com.example.petsall.data.remote.changepets

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
val breedOptions = listOf(
    "Akita",
    "Alaskan Malamute",
    "Basset Hound",
    "Beagle",
    "Bernese Mountain Dog",
    "Bichón Frisé",
    "Bloodhound",
    "Boston Terrier",
    "Boxer",
    "Bulldog",
    "Bulldog Francés",
    "Bulldog Inglés",
    "Cane Corso",
    "Carlino",
    "Chihuahua",
    "Chin Japonés",
    "Chow Chow",
    "Cocker Spaniel",
    "Collie",
    "Corgi",
    "Dachshund",
    "Dálmata",
    "Doberman",
    "Dogo Argentino",
    "Elkhound Noruego",
    "Finnish Spitz",
    "Galgo Italiano",
    "Golden Retriever",
    "Gran Danés",
    "Husky Siberiano",
    "Jack Russell Terrier",
    "Keeshond",
    "Labrador Retriever",
    "Lhasa Apso",
    "Maltese",
    "Mastín",
    "Mastín Inglés",
    "Mastín Tibetano",
    "Old English Sheepdog",
    "Pastor Alemán",
    "Pastor Australiano",
    "Papillón",
    "Pekingese",
    "Pitbull",
    "Pomerania",
    "Poodle",
    "Rhodesian Ridgeback",
    "Rottweiler",
    "Saluki",
    "San Bernardo",
    "Setter Inglés",
    "Setter Irlandés",
    "Shiba Inu",
    "Shih Tzu",
    "Siberian Husky",
    "Terranova",
    "Vizsla",
    "Weimaraner",
    "Whippet",
    "Xoloitzcuintle",
    "Yorkipoo",
    "Yorkshire Terrier",
    "Zuchon"
)

class PAChangePetsDataSource @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) {

    suspend fun getDataPets(selelectedPet: String): List<*>? {
        var listPets: List<*>? = null

        val dataPets =
            firebaseFirestore.collection("Tipo_mascotas").document("YPeGcxkpYMuVQ8xCE2kk").get()
                .await()

        listPets = dataPets.data?.get(selelectedPet) as List<*>

        /*firebaseFirestore.collection("Tipo_mascotas").document("YPeGcxkpYMuVQ8xCE2kk")
            .update("dog", FieldValue.arrayUnion(*breedOptions.toTypedArray())).await()*/

        return listPets
    }
}