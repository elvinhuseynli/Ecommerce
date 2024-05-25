package com.example.e_commerce.ui.reducers.main

import com.example.e_commerce.ui.adapters.CartProductModel
import com.example.e_commerce.ui.intents.main.CartUIState

object CartProductReducer {

    fun getInitialState() = CartUIState()

    fun CartUIState.loading() = copy(isLoading = true)

    fun CartUIState.idle() = copy(isLoading = false)

    fun CartUIState.updateMap(
        map: HashMap<String, Int>
    ) = copy(cartMap = map)

    fun CartUIState.updateData(
        data: List<CartProductModel>
    ) = copy(data = data)

    fun CartUIState.updateTotal(
        total: Float
    ) = copy(totalPrice = total)
}