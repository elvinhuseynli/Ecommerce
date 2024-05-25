package com.example.e_commerce.ui.viewmodels.main

import androidx.lifecycle.viewModelScope
import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.domain.usecases.main.FetchAllProductsUseCase
import com.example.e_commerce.domain.usecases.main.GetCartProductsUseCase
import com.example.e_commerce.domain.usecases.main.UpdateCartProductsUseCase
import com.example.e_commerce.ui.adapters.CartProductModel
import com.example.e_commerce.ui.intents.main.CartUIEffect
import com.example.e_commerce.ui.intents.main.CartUIEvent
import com.example.e_commerce.ui.intents.main.CartUIState
import com.example.e_commerce.ui.reducers.main.CartProductReducer
import com.example.e_commerce.ui.reducers.main.CartProductReducer.idle
import com.example.e_commerce.ui.reducers.main.CartProductReducer.loading
import com.example.e_commerce.ui.reducers.main.CartProductReducer.updateData
import com.example.e_commerce.ui.reducers.main.CartProductReducer.updateMap
import com.example.e_commerce.ui.reducers.main.CartProductReducer.updateTotal
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartProductViewModel @Inject constructor(
    private val fetchAllProductsUseCase: FetchAllProductsUseCase,
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val updateCartProductsUseCase: UpdateCartProductsUseCase
) : MviViewModel<CartUIState, CartUIEvent, CartUIEffect>() {
    override fun getInitialState(): CartUIState = CartProductReducer.getInitialState()

    override fun onEvent(event: CartUIEvent) {
        when (event) {
            is CartUIEvent.OnDeleteButtonClicked -> handleOnDeleteButtonClickedEvent(
                event.userId,
                event.productId
            )

            is CartUIEvent.OnViewCreated -> handleOnViewCreatedEvent(event.userId)
            is CartUIEvent.OnDecrementButtonClicked -> handleOnDecrementButtonClickedEvent(
                event.userId,
                event.productId
            )

            is CartUIEvent.OnIncrementButtonClicked -> handleOnIncrementButtonClickedEvent(
                event.userId,
                event.productId
            )
        }
    }


    private fun calculatePrice() {
        val data = uiState.value.data
        var total = 0f
        for (i in data) {
            total += i.productDetails.price.toFloat()*i.amount
        }
        updateState { updateTotal(total) }
    }

        private fun handleOnViewCreatedEvent(userId: String) {
        viewModelScope.launch {
            updateState { loading() }
            try {
                getCartProductsUseCase.invoke(userId, {
                    trySendEffect { CartUIEffect.ShowMessage(it) }
                }) { map ->
                    viewModelScope.launch {
                        val data: ArrayList<CartProductModel> = arrayListOf()
                        fetchAllProductsUseCase.invoke({
                            trySendEffect { CartUIEffect.ShowMessage(it) }
                        }) { list ->
                            for (i in list) {
                                if (i.productId in map.keys) {
                                    data.add(
                                        CartProductModel(i, map[i.productId]!!)
                                    )
                                }
                            }
                            updateState { updateMap(map) }
                            updateState { updateData(data.toList()) }
                            calculatePrice()
                            updateState { idle() }
                        }
                    }
                }
            } catch (e: Exception) {
                trySendEffect { CartUIEffect.ShowMessage("Something unexpected happened") }
                updateState { idle() }
            }
        }
    }

    private fun handleOnDeleteButtonClickedEvent(userId: String, productId: String) {
        viewModelScope.launch {
            try {
                with(uiState.value) {
                    cartMap.keys.removeAll { it == productId }
                    updateState { updateMap(cartMap) }
                    val list: ArrayList<CartProductModel> = arrayListOf()
                    for (i in data) {
                        if (i.productDetails.productId != productId) {
                            list.add(i)
                        }
                    }
                    updateState { updateData(list.toList()) }
                }
                updateCartProductsUseCase.invoke(userId, uiState.value.cartMap) {
                    calculatePrice()
                }
            } catch (e: Exception) {
                trySendEffect { CartUIEffect.ShowMessage("Something unexpected happened") }
            }
        }
    }

    private fun handleOnIncrementButtonClickedEvent(userId: String, productId: String) {
        viewModelScope.launch {
            try {
                with(uiState.value) {
                    cartMap[productId] = cartMap[productId]!! + 1
                    updateState { updateMap(cartMap) }
                    val list: ArrayList<CartProductModel> = arrayListOf()
                    for (i in data) {
                        if (i.productDetails.productId != productId) {
                            list.add(i)
                        } else {
                            list.add(CartProductModel(i.productDetails, i.amount + 1))
                        }
                    }
                    updateState { updateData(list.toList()) }
                }
                updateCartProductsUseCase.invoke(userId, uiState.value.cartMap) {
                    calculatePrice()
                }
            } catch (e: Exception) {
                trySendEffect { CartUIEffect.ShowMessage("Something unexpected happened") }
            }
        }
    }

    private fun handleOnDecrementButtonClickedEvent(userId: String, productId: String) {
        viewModelScope.launch {
            try {
                with(uiState.value) {
                    if (cartMap[productId]!! > 1) {
                        cartMap[productId] = cartMap[productId]!! - 1
                        updateState { updateMap(cartMap) }
                        val list: ArrayList<CartProductModel> = arrayListOf()
                        for (i in data) {
                            if (i.productDetails.productId != productId) {
                                list.add(i)
                            } else {
                                list.add(CartProductModel(i.productDetails, i.amount - 1))
                            }
                        }
                        updateState { updateData(list.toList()) }
                        updateCartProductsUseCase.invoke(userId, uiState.value.cartMap) {
                            calculatePrice()
                        }
                    } else {
                        handleOnDeleteButtonClickedEvent(userId, productId)
                    }
                }
            } catch (e: Exception) {
                trySendEffect { CartUIEffect.ShowMessage("Something unexpected happened") }
            }
        }
    }
}