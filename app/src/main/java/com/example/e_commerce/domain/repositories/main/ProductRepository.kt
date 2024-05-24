package com.example.e_commerce.domain.repositories.main

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface ProductRepository {

    suspend fun fetchProductsByCategory(category: String): Task<QuerySnapshot>

    suspend fun fetchProducts(): Task<QuerySnapshot>

    suspend fun getFavProducts(userId: String): Task<QuerySnapshot>

    suspend fun updateFavProducts(userId: String): DocumentReference
}