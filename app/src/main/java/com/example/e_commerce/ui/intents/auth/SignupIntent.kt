package com.example.e_commerce.ui.intents.auth

import android.app.Activity
import com.example.e_commerce.core_utils.UIEffect
import com.example.e_commerce.core_utils.UIEvent
import com.example.e_commerce.core_utils.UIState
import com.example.e_commerce.data.models.SignupDataModel

data class SignupUIState(
    val isLoading: Boolean = false,
    val userData: SignupDataModel = SignupDataModel("", "", "", ""),
    val emailAddressError: String = "",
    val passwordError: String = "",
    val passwordRepeatedError: String = "",
    val fullNameError: String = "",
    val isSuccessful: Boolean = false,
    val pinCode: String = "",
    val stateChanged: Boolean = false,
    val timeLeft: String = "",
    val isComplete: Boolean = false
) : UIState


sealed class SignupUIEvent : UIEvent {

    object OnViewCreated : SignupUIEvent()

    data class OnContinueButtonClicked(
        val activity: Activity,
        val emailAddress: String,
        val password: String,
        val passwordRepeated: String,
        val fullName: String
    ) : SignupUIEvent()

    object OnNavigated: SignupUIEvent()

    data class OnCompleteButtonClicked(val enteredOpt: String) : SignupUIEvent()

}

sealed class SignupUIEffect : UIEffect {
    data class ShowMessage(val msg: String) : SignupUIEffect()
}