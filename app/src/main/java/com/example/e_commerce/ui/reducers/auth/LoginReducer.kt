package com.example.e_commerce.ui.reducers.auth

import com.example.e_commerce.ui.intents.auth.LoginUIState

object LoginReducer {

    fun getInitialState() = LoginUIState("","", "", isLoading = false, isSuccessful = false)

    fun LoginUIState.loading() = copy(isLoading = true)

    fun LoginUIState.idle() = copy(isLoading = false)

    fun LoginUIState.fieldValidation(
        emailAddressErrorMsg: String, passwordErrorMsg: String
    ) = copy(emailAddressError = emailAddressErrorMsg, passwordError = passwordErrorMsg)

    fun LoginUIState.updateSuccess() = copy(isSuccessful = true)

    fun LoginUIState.updateUserId(userId: String) = copy(userId = userId)
}