package com.example.e_commerce.domain.usecases.main

import com.example.e_commerce.domain.repositories.main.CartProductRepository
import javax.inject.Inject

class GetCartProductsUseCase @Inject constructor(
    private val repository: CartProductRepository
) {

    suspend operator fun invoke(
        userId: String,
        callback: (String) -> Unit,
        callbackData: (HashMap<String, Int>) -> Unit
    ) {
        repository.getCartProducts(userId).addOnCompleteListener {
            if (it.isSuccessful) {
                if (!it.result.isEmpty) {
                    for (doc in it.result) {
                        val map: HashMap<String, Int> =
                            if (doc.get("cart") != null)
                                doc.get("cart") as HashMap<String, Int>
                            else
                                hashMapOf()
                        callbackData.invoke(map)
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