package com.example.e_commerce.domain.repositories.main

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface CartProductRepository {

    suspend fun getCartProducts(userId: String): Task<QuerySnapshot>

    suspend fun updateCartProducts(userId: String): DocumentReference

}