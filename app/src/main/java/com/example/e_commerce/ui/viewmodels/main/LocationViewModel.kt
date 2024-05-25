package com.example.e_commerce.ui.viewmodels.main

import androidx.lifecycle.viewModelScope
import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.data.models.main.LocationDataModel
import com.example.e_commerce.domain.usecases.main.GetLocationsUseCase
import com.example.e_commerce.ui.intents.main.LocationUIEffect
import com.example.e_commerce.ui.intents.main.LocationUIEvent
import com.example.e_commerce.ui.intents.main.LocationUIState
import com.example.e_commerce.ui.reducers.main.LocationReducer
import com.example.e_commerce.ui.reducers.main.LocationReducer.clearSearchData
import com.example.e_commerce.ui.reducers.main.LocationReducer.idle
import com.example.e_commerce.ui.reducers.main.LocationReducer.loading
import com.example.e_commerce.ui.reducers.main.LocationReducer.updateData
import com.example.e_commerce.ui.reducers.main.LocationReducer.updateSearchData
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) :MviViewModel<LocationUIState, LocationUIEvent, LocationUIEffect>() {
    override fun getInitialState(): LocationUIState = LocationReducer.getInitialState()

    override fun onEvent(event: LocationUIEvent) {
        when(event){
            LocationUIEvent.OnViewCreated -> handleOnViewCreatedEvent()
            is LocationUIEvent.OnSearchViewChange -> handleOnSearchViewChangeEvent(event.searchText)
        }
    }

    private fun handleOnSearchViewChangeEvent(searchText: String) {
        updateState { clearSearchData() }
        val searchList = arrayListOf<LocationDataModel>()
        if (searchText.isNotEmpty()) {
            uiState.value.data.forEach {
                if (it.addressTitle.lowercase().contains(searchText)) {
                    searchList.add(it)
                }
            }
        } else {
            searchList.addAll(uiState.value.data)
        }

        updateState { updateSearchData(searchList.toList()) }
    }

    private fun handleOnViewCreatedEvent() {
        viewModelScope.launch {
            updateState { loading() }
            try {
                var data : List<LocationDataModel>
                getLocationsUseCase.invoke({
                    if(it!="success") {
                        trySendEffect { LocationUIEffect.ShowMessage(it) }
                    }
                }) {
                    data = it
                    updateState { updateData(data) }
                    updateState { updateSearchData(data) }
                    updateState { idle() }
                }
            } catch (e: Exception) {
                trySendEffect { LocationUIEffect.ShowMessage("Something unexpected happened") }
                updateState { idle() }
            }
        }
    }
}