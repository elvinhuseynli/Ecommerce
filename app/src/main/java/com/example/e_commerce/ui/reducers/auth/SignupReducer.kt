package com.example.e_commerce.ui.reducers.auth

import com.example.e_commerce.data.models.SignupDataModel
import com.example.e_commerce.ui.intents.auth.SignupUIState

object SignupReducer {

    fun getInitialState() = SignupUIState()

    fun SignupUIState.loading() = copy(isLoading = true)
    fun SignupUIState.idle() = copy(isLoading = false)

    fun SignupUIState.fieldStateValidation(
        emailAddressErrorMsg: String,
        fullNameErrorMsg: String,
        passwordErrorMsg: String,
        passwordRepeatedErrorMsg: String,
    ) = copy(
        emailAddressError = emailAddressErrorMsg,
        fullNameError = fullNameErrorMsg,
        passwordError = passwordErrorMsg,
        passwordRepeatedError = passwordRepeatedErrorMsg
    )

    fun SignupUIState.updateSuccess() = copy(isSuccessful = true)

    fun SignupUIState.resetSuccess() = copy(isSuccessful = false)

    fun SignupUIState.updateUserData(
        emailAddress: String,
        fullName: String,
        password: String,
        salt: String
    ) = copy(
        userData = SignupDataModel(
            emailAddress,
            fullName,
            password,
            salt
        )
    )

    fun SignupUIState.updatePinCode(
        pinCode: String
    ) = copy(pinCode = pinCode)

    fun SignupUIState.updateTimerState(
        state: Boolean
    ) = copy(stateChanged = state)

    fun SignupUIState.updateTimeLeft(
        time: String
    ) = copy(timeLeft = time)

    fun SignupUIState.updateCompletionState() = copy(isComplete=true)
}