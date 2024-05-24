package com.example.e_commerce.domain.usecases.main

import com.example.e_commerce.domain.repositories.main.ProductRepository
import javax.inject.Inject

class GetFavProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        userId: String,
        callback: (String) -> Unit,
        callbackData: (ArrayList<String>)->Unit
    ) {
        repository.getFavProducts(userId)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (!it.result.isEmpty) {
                        for (doc in it.result) {
                            val list: ArrayList<String> =
                                if (doc.get("favorites") != null)
                                    (doc.get("favorites") as ArrayList<String>)
                                else arrayListOf()
                            callbackData.invoke(list)
                        }
                    } else {
                        callback.invoke("No data available")
                    }
                } else {
                    callback.invoke("Server error occurred")
                }
            }
    }
}