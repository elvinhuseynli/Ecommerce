package com.example.e_commerce.data.services

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

interface LoginService {
    suspend fun getUserByEmail(emailAddress: String): Task<QuerySnapshot> {
        return Firebase.firestore.collection("users").whereEqualTo("emailAddress", emailAddress).get()
    }
}