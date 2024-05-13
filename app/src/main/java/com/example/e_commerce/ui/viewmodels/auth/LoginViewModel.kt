package com.example.e_commerce.ui.viewmodels.auth

import android.app.Activity
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.domain.usecases.auth.GetUserFromLocalUseCase
import com.example.e_commerce.domain.usecases.auth.LoginUseCase
import com.example.e_commerce.ui.intents.auth.LoginUIEffect
import com.example.e_commerce.ui.intents.auth.LoginUIEvent
import com.example.e_commerce.ui.intents.auth.LoginUIState
import com.example.e_commerce.ui.reducers.auth.LoginReducer
import com.example.e_commerce.ui.reducers.auth.LoginReducer.fieldValidation
import com.example.e_commerce.ui.reducers.auth.LoginReducer.idle
import com.example.e_commerce.ui.reducers.auth.LoginReducer.loading
import com.example.e_commerce.ui.reducers.auth.LoginReducer.updateSuccess
import com.example.e_commerce.ui.reducers.auth.LoginReducer.updateUserId
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getUserFromLocalUseCase: GetUserFromLocalUseCase
) : MviViewModel<LoginUIState, LoginUIEvent, LoginUIEffect>() {

    override fun getInitialState(): LoginUIState = LoginReducer.getInitialState()

    override fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.OnCreate -> handleOnCreateEvent(event.activity)
            is LoginUIEvent.OnLoginButtonClicked -> handleOnLoginButtonClickedEvent(
                event.activity,
                event.emailAddress,
                event.password
            )
        }
    }


    private fun handleOnLoginButtonClickedEvent(
        activity: Activity,
        emailAddress: String,
        password: String
    ) {

        val emailErrorText =
            if (!emailAddress.matches(activity.getString(R.string.email_regex).toRegex()))
                "Invalid email format"
            else
                ""

        val passwordErrorText =
            if (!password.matches(activity.getString(R.string.password_regex).toRegex()))
                "Invalid password format"
            else
                ""

        updateState { fieldValidation(emailErrorText, passwordErrorText) }


        if (uiState.value.emailAddressError.isEmpty() && uiState.value.passwordError.isEmpty()) {
            viewModelScope.launch {
                updateState { loading() }
                try {
                    loginUseCase.invoke(activity, emailAddress, password) {
                        if (it == "success") {
                            updateState { updateSuccess() }
                        } else {
                            trySendEffect { LoginUIEffect.ShowMessage(it) }
                        }
                    }
                } catch (e: Exception) {
                    trySendEffect { LoginUIEffect.ShowMessage("Something unexpected happened") }
                } finally {
                    updateState { idle() }
                }
            }
        }
    }

    private fun handleOnCreateEvent(activity: Activity) {
        viewModelScope.launch {
            val res = getUserFromLocalUseCase(activity)
            updateState { updateUserId(res ?: "") }
        }
    }
}