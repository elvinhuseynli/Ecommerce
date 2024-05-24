package com.example.e_commerce.domain.usecases.main

import com.example.e_commerce.domain.repositories.main.ProductRepository
import javax.inject.Inject

class UpdateFavProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        userId: String,
        productId: String,
        favoritesList: ArrayList<String>,
        callbackData: (ArrayList<String>) -> Unit
    ) {
        if (productId in favoritesList) {
            favoritesList.removeAll { it == productId }
        } else {
            favoritesList.add(productId)
        }
        repository.updateFavProducts(userId)
            .update(
                "favorites", favoritesList
            ).addOnSuccessListener {
                callbackData.invoke(favoritesList)
            }
    }
}