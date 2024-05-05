package com.example.e_commerce.ui.intents.auth

import android.app.Activity
import com.example.e_commerce.core_utils.UIEffect
import com.example.e_commerce.core_utils.UIEvent
import com.example.e_commerce.core_utils.UIState

data class LoginUIState(
    val emailAddressError: String = "",
    val passwordError: String = "",
    val userId: String = "",
    val isLoading: Boolean = false,
    val isSuccessful: Boolean = false
) : UIState

sealed class LoginUIEvent : UIEvent {
    data class OnCreate(val activity: Activity) : LoginUIEvent()
    data class OnLoginButtonClicked(val activity: Activity, val emailAddress: String, val password: String) : LoginUIEvent()
}

sealed class LoginUIEffect : UIEffect {
    data class ShowMessage(val activity: Activity, val msg: String) : LoginUIEffect()
}