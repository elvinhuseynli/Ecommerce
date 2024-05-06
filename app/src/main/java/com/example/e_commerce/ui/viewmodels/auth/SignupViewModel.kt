package com.example.e_commerce.ui.viewmodels.auth

import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.ui.intents.auth.SignupUIEffect
import com.example.e_commerce.ui.intents.auth.SignupUIEvent
import com.example.e_commerce.ui.intents.auth.SignupUIState
import com.example.e_commerce.ui.reducers.auth.SignupReducer
import javax.inject.Inject

class SignupViewModel (

) :MviViewModel<SignupUIState, SignupUIEvent, SignupUIEffect>() {
    override fun getInitialState(): SignupUIState = SignupReducer.getInitialState()

    override fun onEvent(event: SignupUIEvent) {
        when(event) {
            is SignupUIEvent.OnCompleteButtonClicked -> handleOnCompleteButtonClickedEvent()
            is SignupUIEvent.OnContinueButtonClicked -> handleOnContinueButtonClicked()
        }
    }

    private fun handleOnContinueButtonClicked() {

    }

    private fun handleOnCompleteButtonClickedEvent() {

    }
}