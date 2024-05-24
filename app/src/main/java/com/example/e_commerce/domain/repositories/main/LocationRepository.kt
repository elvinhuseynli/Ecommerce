package com.example.e_commerce.domain.repositories.main

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface LocationRepository {

    suspend fun fetchLocations(): Task<QuerySnapshot>
}