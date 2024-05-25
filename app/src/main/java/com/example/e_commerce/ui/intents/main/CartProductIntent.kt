package com.example.e_commerce.ui.intents.main

import com.example.e_commerce.core_utils.UIEffect
import com.example.e_commerce.core_utils.UIEvent
import com.example.e_commerce.core_utils.UIState
import com.example.e_commerce.ui.adapters.CartProductModel

data class CartUIState(
    val cartMap: HashMap<String, Int> = hashMapOf(),
    val data: List<CartProductModel> = listOf(),
    val isLoading: Boolean = false,
    val totalPrice: Float = 0f,
): UIState

sealed class CartUIEvent: UIEvent {
    data class OnViewCreated(val userId: String): CartUIEvent()

    data class OnDeleteButtonClicked(val userId: String, val productId:String): CartUIEvent()

    data class OnIncrementButtonClicked(val userId: String, val productId:String): CartUIEvent()

    data class OnDecrementButtonClicked(val userId: String, val productId:String): CartUIEvent()

}

sealed class CartUIEffect: UIEffect {
    data class ShowMessage(val msg: String): CartUIEffect()
}