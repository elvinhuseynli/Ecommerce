package com.example.e_commerce.ui.reducers.main

import com.example.e_commerce.data.models.main.ProductDataModel
import com.example.e_commerce.ui.intents.main.ProductUIState

object ProductReducer {

    fun getInitialState() = ProductUIState()

    fun ProductUIState.loading() = copy(isLoading=true)
    fun ProductUIState.idle() = copy(isLoading=false)

    fun ProductUIState.updateData(
        productData: List<ProductDataModel>
    ) = copy(data = productData)

    fun ProductUIState.updateSearchData(
        updatedList: List<ProductDataModel>
    ) = copy(updatedList = updatedList)

    fun ProductUIState.clearSearchData() = copy(updatedList = listOf())

    fun ProductUIState.updateFavoritesList(
        favoritesList: ArrayList<String>
    ) = copy(favoritesList = favoritesList)

    fun ProductUIState.updateCart(
        cart: HashMap<String, Int>
    ) = copy(cart = cart)
}