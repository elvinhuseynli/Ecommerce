package com.example.e_commerce.data.repositoriesImpl.auth

import com.example.e_commerce.domain.repositories.SignupRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore

class SignupRepositoryImpl: SignupRepository {
    override suspend fun addUserToDatabase(): CollectionReference {
        return Firebase.firestore.collection("users")
    }
}