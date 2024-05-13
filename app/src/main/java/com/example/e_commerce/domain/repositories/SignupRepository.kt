package com.example.e_commerce.domain.repositories

import com.google.firebase.firestore.CollectionReference

interface SignupRepository {

    suspend fun addUserToDatabase(): CollectionReference
}