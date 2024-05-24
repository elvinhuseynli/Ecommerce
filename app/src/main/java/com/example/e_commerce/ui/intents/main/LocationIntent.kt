package com.example.e_commerce.ui.intents.main

import com.example.e_commerce.core_utils.UIEffect
import com.example.e_commerce.core_utils.UIEvent
import com.example.e_commerce.core_utils.UIState
import com.example.e_commerce.data.models.auth.LocationDataModel

data class LocationUIState(
    val data: List<LocationDataModel> = listOf(),
    val isLoading: Boolean = false,
    val updatedList: List<LocationDataModel> = listOf()
) : UIState

sealed class LocationUIEvent: UIEvent {

    object OnViewCreated: LocationUIEvent()
    data class OnSearchViewChange(val searchText: String) : LocationUIEvent()
}

sealed class LocationUIEffect: UIEffect {
    data class ShowMessage(val msg: String): LocationUIEffect()
}