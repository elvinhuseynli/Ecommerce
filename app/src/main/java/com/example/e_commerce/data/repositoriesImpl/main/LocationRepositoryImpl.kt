package com.example.e_commerce.data.repositoriesImpl.main

import com.example.e_commerce.domain.repositories.main.LocationRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

class LocationRepositoryImpl: LocationRepository {
    override suspend fun fetchLocations(): Task<QuerySnapshot> {
        return Firebase.firestore
            .collection("locations")
            .get()
    }
}