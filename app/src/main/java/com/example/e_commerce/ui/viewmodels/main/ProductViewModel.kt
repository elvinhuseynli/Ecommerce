package com.example.e_commerce.ui.viewmodels.main

import androidx.lifecycle.viewModelScope
import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.data.models.auth.ProductDataModel
import com.example.e_commerce.domain.usecases.main.FetchAllProductsUseCase
import com.example.e_commerce.domain.usecases.main.FetchProductsByCategoryUseCase
import com.example.e_commerce.domain.usecases.main.GetFavProductsUseCase
import com.example.e_commerce.domain.usecases.main.UpdateFavProductsUseCase
import com.example.e_commerce.ui.intents.main.ProductUIEffect
import com.example.e_commerce.ui.intents.main.ProductUIEvent
import com.example.e_commerce.ui.intents.main.ProductUIState
import com.example.e_commerce.ui.reducers.main.ProductReducer
import com.example.e_commerce.ui.reducers.main.ProductReducer.clearSearchData
import com.example.e_commerce.ui.reducers.main.ProductReducer.idle
import com.example.e_commerce.ui.reducers.main.ProductReducer.loading
import com.example.e_commerce.ui.reducers.main.ProductReducer.updateData
import com.example.e_commerce.ui.reducers.main.ProductReducer.updateFavoritesList
import com.example.e_commerce.ui.reducers.main.ProductReducer.updateSearchData
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val fetchProductsByCategoryUseCase: FetchProductsByCategoryUseCase,
    private val fetchAllProductsUseCase: FetchAllProductsUseCase,
    private val updateFavProductsUseCase: UpdateFavProductsUseCase,
    private val getFavProductsUseCase: GetFavProductsUseCase
) : MviViewModel<ProductUIState, ProductUIEvent, ProductUIEffect>() {
    override fun getInitialState(): ProductUIState = ProductReducer.getInitialState()

    override fun onEvent(event: ProductUIEvent) {
        when (event) {
            is ProductUIEvent.OnAddButtonClicked -> handleOnAddButtonClickedEvent(
                event.userId,
                event.productId
            )

            is ProductUIEvent.OnAllViewCreated -> handleOnAllViewCreatedEvent(event.userId)
            is ProductUIEvent.OnCategoryViewCreated -> handleOnCategoryViewCreated(
                event.userId,
                event.category
            )

            is ProductUIEvent.OnSearchViewChange -> handleOnSearchViewChangeEvent(event.searchText)
            is ProductUIEvent.OnFavButtonClicked -> handleOnFavButtonClickedEvent(
                event.userId,
                event.productId
            )

            is ProductUIEvent.OnFavoriteViewCreated -> handleOnFavoriteViewCreatedEvent(event.userId)
        }
    }

    private fun handleOnFavoriteViewCreatedEvent(userId: String) {
        viewModelScope.launch {
            updateState { loading() }
            try {
                fetchAllProductsUseCase.invoke({
                    if (it != "success") {
                        trySendEffect { ProductUIEffect.ShowMessage(it) }
                    }
                }) {
                    val data: List<ProductDataModel> = it
                    viewModelScope.launch {
                        getFavProductsUseCase.invoke(userId, {
                            trySendEffect { ProductUIEffect.ShowMessage(it) }
                        }) { favList ->
                            updateState { updateFavoritesList(favList) }
                            val tempData = arrayListOf<ProductDataModel>()
                            for (i in data) {
                                if (favList.contains(i.productId)) {
                                    tempData.add(i)
                                }
                            }
                            updateState { updateData(tempData.toList()) }
                            updateState { updateSearchData(tempData.toList()) }
                            updateState { idle() }
                        }
                    }
                }


            } catch (e: Exception) {
                trySendEffect { ProductUIEffect.ShowMessage("Something unexpected happened") }
                updateState { idle() }
            }
        }
    }

    private fun handleOnFavButtonClickedEvent(userId: String, productId: String) {
        viewModelScope.launch {
            try {
                updateFavProductsUseCase.invoke(userId, productId, uiState.value.favoritesList) {
                    updateState { updateFavoritesList(it) }
                }
            } catch (e: Exception) {
                trySendEffect { ProductUIEffect.ShowMessage("Something unexpected happened") }
            }
        }
    }

    private fun handleOnSearchViewChangeEvent(searchText: String) {
        updateState { clearSearchData() }
        val searchList = arrayListOf<ProductDataModel>()
        if (searchText.isNotEmpty()) {
            uiState.value.data.forEach {
                if (it.productName.lowercase().contains(searchText)
                    || it.producerCompany.lowercase().contains(searchText)
                ) {
                    searchList.add(it)
                }
            }
        } else {
            searchList.addAll(uiState.value.data)
        }

        updateState { updateSearchData(searchList.toList()) }
    }

    private fun handleOnCategoryViewCreated(userId: String, category: String) {
        viewModelScope.launch {
            updateState { loading() }
            try {
                var data: List<ProductDataModel>
                fetchProductsByCategoryUseCase.invoke(category, {
                    if (it != "success") {
                        trySendEffect { ProductUIEffect.ShowMessage(it) }
                    }
                }) {
                    data = it
                    updateState { updateData(data) }
                    updateState { updateSearchData(data) }
                    updateState { idle() }
                }
                getFavProductsUseCase.invoke(userId, {
                    trySendEffect { ProductUIEffect.ShowMessage(it) }
                }) {
                    updateState { updateFavoritesList(it) }
                }

            } catch (e: Exception) {
                trySendEffect { ProductUIEffect.ShowMessage("Something unexpected happened") }
                updateState { idle() }
            }
        }
    }

    private fun handleOnAllViewCreatedEvent(userId: String) {
        viewModelScope.launch {
            updateState { loading() }
            try {
                var data: List<ProductDataModel>
                fetchAllProductsUseCase.invoke({
                    if (it != "success") {
                        trySendEffect { ProductUIEffect.ShowMessage(it) }
                    }
                }) {
                    data = it
                    updateState { updateData(data) }
                    updateState { updateSearchData(data) }
                    updateState { idle() }
                }
                getFavProductsUseCase.invoke(userId, {
                    trySendEffect { ProductUIEffect.ShowMessage(it) }
                }) {
                    updateState { updateFavoritesList(it) }
                }
            } catch (e: Exception) {
                trySendEffect { ProductUIEffect.ShowMessage("Something unexpected happened") }
                updateState { idle() }
            }
        }
    }

    private fun handleOnAddButtonClickedEvent(userId: String, productId: String) {

    }
}