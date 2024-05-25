package com.example.e_commerce.ui.reducers.main

import com.example.e_commerce.data.models.main.LocationDataModel
import com.example.e_commerce.ui.intents.main.LocationUIState

object LocationReducer {

    fun getInitialState() = LocationUIState()

    fun LocationUIState.loading() = copy(isLoading = true)

    fun LocationUIState.idle() = copy(isLoading = false)

    fun LocationUIState.updateData(
        productData: List<LocationDataModel>
    ) = copy(data = productData)

    fun LocationUIState.clearSearchData() = copy(updatedList = listOf())

    fun LocationUIState.updateSearchData(
        updatedList: List<LocationDataModel>
    ) = copy(updatedList = updatedList)
}