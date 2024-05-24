package com.example.e_commerce.ui.intents.main

import android.app.Activity
import com.example.e_commerce.core_utils.UIEffect
import com.example.e_commerce.core_utils.UIEvent
import com.example.e_commerce.core_utils.UIState


data class ChangePassUIState(
    val passwordError: String = "",
    val passwordRepeatedError: String = "",
    val isSuccessful: Boolean = false
) : UIState


sealed class ChangePassUIEvent : UIEvent {
    data class OnUpdateButtonClicked(
        val activity: Activity,
        val userId: String,
        val password: String,
        val passwordRepeated: String
    ) : ChangePassUIEvent()
}

sealed class ChangePassUIEffect : UIEffect {
    data class ShowMessage(val msg: String) : ChangePassUIEffect()
}