package com.example.e_commerce.ui.viewmodels.auth

import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.ui.intents.auth.SignupUIEffect
import com.example.e_commerce.ui.intents.auth.SignupUIEvent
import com.example.e_commerce.ui.intents.auth.SignupUIState
import javax.inject.Inject

class SignupViewModel (

) :MviViewModel<SignupUIState, SignupUIEvent, SignupUIEffect>() {
    override fun getInitialState(): SignupUIState {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: SignupUIEvent) {
        TODO("Not yet implemented")
    }
}