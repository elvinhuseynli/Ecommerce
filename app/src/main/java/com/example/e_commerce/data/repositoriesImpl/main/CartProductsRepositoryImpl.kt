package com.example.e_commerce.data.repositoriesImpl.main

import com.example.e_commerce.domain.repositories.main.CartProductRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class CartProductsRepositoryImpl: CartProductRepository {
    override suspend fun getCartProducts(userId: String): Task<QuerySnapshot> {
        return Firebase.firestore.collection("users")
            .whereEqualTo(FieldPath.documentId(), userId)
            .get()
    }

    override suspend fun updateCartProducts(userId: String): DocumentReference {
        return Firebase.firestore.collection("users")
            .document(userId)
    }
}