package com.example.e_commerce.ui.viewmodels.main

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.domain.usecases.main.UpdatePasswordUseCase
import com.example.e_commerce.ui.intents.main.ChangePassUIEffect
import com.example.e_commerce.ui.intents.main.ChangePassUIEvent
import com.example.e_commerce.ui.intents.main.ChangePassUIState
import com.example.e_commerce.ui.reducers.main.ChangePasswordReducer
import com.example.e_commerce.ui.reducers.main.ChangePasswordReducer.fieldValidation
import com.example.e_commerce.ui.reducers.main.ChangePasswordReducer.updateSuccess
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : MviViewModel<ChangePassUIState, ChangePassUIEvent, ChangePassUIEffect>() {
    override fun getInitialState(): ChangePassUIState = ChangePasswordReducer.getInitialState()

    override fun onEvent(event: ChangePassUIEvent) {
        when (event) {
            is ChangePassUIEvent.OnUpdateButtonClicked -> handleOnUpdateButtonClickedEvent(
                event.activity,
                event.password,
                event.passwordRepeated,
                event.userId
            )
        }
    }

    private fun handleOnUpdateButtonClickedEvent(
        activity: Activity,
        password: String,
        passwordRepeated: String,
        userId: String
    ) {


        val passwordErrorText =
            if (!password.matches(activity.getString(R.string.password_regex).toRegex()))
                "Invalid password format"
            else
                ""

        val passwordRepeatedErrorText =
            if (!password.matches(activity.getString(R.string.password_regex).toRegex()))
                "Invalid password format"
            else
                ""

        updateState { fieldValidation(passwordErrorText, passwordRepeatedErrorText) }

        if (uiState.value.passwordError.isEmpty() && uiState.value.passwordRepeatedError.isEmpty()) {
            if (password == passwordRepeated) {
                viewModelScope.launch {
                    try {
                        updatePasswordUseCase.invoke(userId, password) {
                            if (it == "success") {
                                updateState { updateSuccess() }
                            } else {
                                trySendEffect { ChangePassUIEffect.ShowMessage(it) }
                            }
                        }
                    } catch (e: Exception) {
                        trySendEffect { ChangePassUIEffect.ShowMessage("Something unexpected happened") }
                    }
                }
            } else {
                trySendEffect { ChangePassUIEffect.ShowMessage("Passwords don't match") }
            }
        }

    }


}