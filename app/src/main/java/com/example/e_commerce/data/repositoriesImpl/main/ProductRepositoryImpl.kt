package com.example.e_commerce.data.repositoriesImpl.main

import com.example.e_commerce.domain.repositories.main.ProductRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class ProductRepositoryImpl: ProductRepository {
    override suspend fun fetchProductsByCategory(category: String): Task<QuerySnapshot> {
        return Firebase.firestore
            .collection("products")
            .whereEqualTo("category", category)
            .get()
    }

    override suspend fun fetchProducts(): Task<QuerySnapshot> {
        return Firebase.firestore
            .collection("products")
            .get()
    }

    override suspend fun getFavProducts(userId: String): Task<QuerySnapshot> {
        return Firebase.firestore.collection("users")
            .whereEqualTo(FieldPath.documentId(), userId)
            .get()
    }

    override suspend fun updateFavProducts(userId: String): DocumentReference {
        return Firebase.firestore.collection("users")
            .document(userId)
    }
}