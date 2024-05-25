package com.example.e_commerce.domain.usecases.main

import com.example.e_commerce.domain.repositories.main.CartProductRepository
import javax.inject.Inject

class UpdateCartProductsUseCase @Inject constructor(
    private val repository: CartProductRepository
) {

    suspend operator fun invoke(
        userId: String,
        cart: HashMap<String, Int>,
        callbackData: (HashMap<String, Int>)->Unit
    ) {

        repository.updateCartProducts(userId)
            .update("cart", cart)
            .addOnSuccessListener {
                callbackData.invoke(cart)
            }
    }
}