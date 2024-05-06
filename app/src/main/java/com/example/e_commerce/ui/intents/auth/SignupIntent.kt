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
) : UIState


sealed class SignupUIEvent : UIEvent {
    data class OnContinueButtonClicked(
        val emailAddress: String,
        val password: String,
        val passwordRepeated: String,
        val fullName: String
    ) : SignupUIEvent()

    data class OnCompleteButtonClicked(val pinCode: String) : SignupUIEvent()
}

sealed class SignupUIEffect : UIEffect {
    data class ShowMessage(val activity: Activity, val msg: String) : SignupUIEffect()
}