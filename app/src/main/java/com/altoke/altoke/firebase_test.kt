package com.altoke.altoke

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun main(){
    val database = Firebase.firestore

    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )

    database.collection("users")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d("Docsnippets", "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener{e ->
            Log.w("docsnippets", "erroradding document", e)
        }
}

// val city = hashMapOf(
//     "name" to "Los Angeles",
//     "state" to "CA",
//     "country" to "USA"
// )