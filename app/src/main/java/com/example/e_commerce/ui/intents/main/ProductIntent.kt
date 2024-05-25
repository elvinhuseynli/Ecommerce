package com.example.e_commerce.ui.intents.main

import com.example.e_commerce.core_utils.UIEffect
import com.example.e_commerce.core_utils.UIEvent
import com.example.e_commerce.core_utils.UIState
import com.example.e_commerce.data.models.auth.ProductDataModel


data class ProductUIState(
    val data: List<ProductDataModel> = listOf(),
    val isLoading: Boolean = false,
    val updatedList: List<ProductDataModel> = listOf(),
    val favoritesList: ArrayList<String> = arrayListOf()
) : UIState

sealed class ProductUIEvent : UIEvent {
    data class OnCategoryViewCreated(val userId: String, val category: String) : ProductUIEvent()
    data class OnSearchViewChange(val searchText: String) : ProductUIEvent()
    data class OnAllViewCreated(val userId: String) : ProductUIEvent()

    data class OnFavoriteViewCreated(val userId: String) : ProductUIEvent()

    data class OnAddButtonClicked(
        val userId: String,
        val productId: String
    ) : ProductUIEvent()

    data class OnFavButtonClicked(
        val userId: String,
        val productId: String
    ) : ProductUIEvent()
}

sealed class ProductUIEffect : UIEffect {
    data class ShowMessage(val msg: String) : ProductUIEffect()
}