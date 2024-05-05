package com.example.e_commerce.core_utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


interface UIState
interface UIEffect
interface UIEvent

abstract class MviViewModel<State: UIState, Event: UIEvent, Effect: UIEffect> :
    ViewModel() {

    protected val mutableUIState: MutableStateFlow<State> by lazy { MutableStateFlow(getInitialState()) }
    val uiState: StateFlow<State> by lazy { mutableUIState }

    protected val mutableUIEffect: MutableSharedFlow<Effect> by lazy { MutableSharedFlow(extraBufferCapacity = 1) }
    val uiEffect : SharedFlow<Effect> = mutableUIEffect

    abstract fun getInitialState(): State

    abstract fun onEvent(event: Event)

    protected suspend fun sendEffect(effect: ()->Effect) {
        mutableUIEffect.emit(effect())
    }

    protected fun trySendEffect(effect: () -> Effect) {
        mutableUIEffect.tryEmit(effect())
    }

    protected fun updateState(modifier: State.()-> State) {
        mutableUIState.update(modifier)
    }

}