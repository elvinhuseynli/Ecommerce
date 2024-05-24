package com.example.e_commerce.ui.reducers.main

import com.example.e_commerce.ui.intents.main.ChangePassUIState

object ChangePasswordReducer {

    fun getInitialState() = ChangePassUIState()

    fun ChangePassUIState.updateSuccess() = copy(isSuccessful = true)

    fun ChangePassUIState.fieldValidation(
        passwordErrorMsg: String,
        passwordRepeatedErrorMsg: String
    ) = copy(passwordError = passwordErrorMsg, passwordRepeatedError = passwordRepeatedErrorMsg)
}