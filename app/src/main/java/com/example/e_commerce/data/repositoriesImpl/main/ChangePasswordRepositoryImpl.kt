package com.example.e_commerce.data.repositoriesImpl.main

import com.example.e_commerce.domain.repositories.main.ChangePasswordRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class ChangePasswordRepositoryImpl: ChangePasswordRepository {
    override suspend fun getUserById(userId: String): Task<QuerySnapshot> {
        return Firebase.firestore
            .collection("users")
            .whereEqualTo(FieldPath.documentId(), userId)
            .get()
    }
}