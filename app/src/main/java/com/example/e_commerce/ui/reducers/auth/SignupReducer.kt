package com.example.e_commerce.ui.reducers.auth

import com.example.e_commerce.ui.intents.auth.SignupUIState

object SignupReducer {

    fun getInitialState() = SignupUIState()

    fun SignupUIState.loading() = copy(isLoading=true)
    fun SignupUIState.idle() = copy(isLoading=false)

    fun SignupUIState.fieldStateValidation(
        emailAddressErrorMsg: String,
        fullNameErrorMsg: String,
        passwordErrorMsg: String,
        passwordRepeatedErrorMsg: String,
    ) = copy(
        emailAddressError=emailAddressErrorMsg,
        fullNameError = fullNameErrorMsg,
        passwordError = passwordErrorMsg,
        passwordRepeatedError = passwordRepeatedErrorMsg
    )
}