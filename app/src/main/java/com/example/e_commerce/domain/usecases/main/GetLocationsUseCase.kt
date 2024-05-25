package com.example.e_commerce.domain.usecases.main

import com.example.e_commerce.data.models.main.LocationDataModel
import com.example.e_commerce.domain.repositories.main.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(
        callback: (String)->Unit,
        callbackData: (List<LocationDataModel>)->Unit
    ) {

        repository.fetchLocations().addOnCompleteListener {
            if(it.isSuccessful) {
                if(!it.result.isEmpty) {
                    val data = arrayListOf<LocationDataModel>()
                    for(doc in it.result) {
                        data.add(
                            LocationDataModel(
                                doc.getString("id").orEmpty(),
                                doc.getString("addressTitle").orEmpty(),
                                doc.getString("latitude").orEmpty(),
                                doc.getString("longitude").orEmpty(),
                            )
                        )
                    }
                    callback.invoke("success")
                    callbackData.invoke(data)
                } else {
                    callback.invoke("No data available")
                }
            } else {
                callback.invoke("Server error occurred")
            }
        }
    }
}