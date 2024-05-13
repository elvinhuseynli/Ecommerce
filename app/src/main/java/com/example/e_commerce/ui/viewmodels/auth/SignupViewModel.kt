package com.example.e_commerce.ui.viewmodels.auth

import android.app.Activity
import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.MviViewModel
import com.example.e_commerce.domain.usecases.auth.AddUserToRemoteUseCase
import com.example.e_commerce.domain.usecases.auth.CheckExistenceUseCase
import com.example.e_commerce.domain.usecases.auth.SendMailUseCase
import com.example.e_commerce.ui.intents.auth.SignupUIEffect
import com.example.e_commerce.ui.intents.auth.SignupUIEvent
import com.example.e_commerce.ui.intents.auth.SignupUIState
import com.example.e_commerce.ui.reducers.auth.SignupReducer
import com.example.e_commerce.ui.reducers.auth.SignupReducer.fieldStateValidation
import com.example.e_commerce.ui.reducers.auth.SignupReducer.idle
import com.example.e_commerce.ui.reducers.auth.SignupReducer.loading
import com.example.e_commerce.ui.reducers.auth.SignupReducer.resetSuccess
import com.example.e_commerce.ui.reducers.auth.SignupReducer.updateCompletionState
import com.example.e_commerce.ui.reducers.auth.SignupReducer.updatePinCode
import com.example.e_commerce.ui.reducers.auth.SignupReducer.updateSuccess
import com.example.e_commerce.ui.reducers.auth.SignupReducer.updateTimeLeft
import com.example.e_commerce.ui.reducers.auth.SignupReducer.updateTimerState
import com.example.e_commerce.ui.reducers.auth.SignupReducer.updateUserData
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

class SignupViewModel @Inject constructor(
    private val checkExistenceUseCase: CheckExistenceUseCase,
    private val sendMailUseCase: SendMailUseCase,
    private val addUserToRemoteUseCase: AddUserToRemoteUseCase
) : MviViewModel<SignupUIState, SignupUIEvent, SignupUIEffect>() {

    override fun getInitialState(): SignupUIState = SignupReducer.getInitialState()

    override fun onEvent(event: SignupUIEvent) {
        when (event) {
            is SignupUIEvent.OnCompleteButtonClicked -> handleOnCompleteButtonClickedEvent(event.enteredOpt)
            is SignupUIEvent.OnContinueButtonClicked -> handleOnContinueButtonClickedEvent(
                event.activity,
                event.emailAddress,
                event.password,
                event.passwordRepeated,
                event.fullName,
            )

            is SignupUIEvent.OnViewCreated -> handleOnViewCreatedEvent()
            SignupUIEvent.OnNavigated -> handleOnNavigatedEvent()
        }
    }

    private fun initializeTimer() {
        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                var minutes = 0
                if (seconds >= 60) {
                    minutes = seconds / 60
                    seconds %= 60
                }
                val format = DecimalFormat("00")
                val formattedTime = "${format.format(minutes)}:${format.format(seconds)}"
                updateState { updateTimeLeft(formattedTime) }
            }

            override fun onFinish() {
                updateState { updateTimerState(false) }
            }
        }

        timer.start()
    }

    private fun handleOnNavigatedEvent() {
        updateState { resetSuccess() }
    }

    private fun handleOnViewCreatedEvent() {
        viewModelScope.launch {
            try {
                sendMailUseCase.invoke(uiState.value.userData.emailAddress) {
                    updateState { updatePinCode(it) }
                    updateState { updateTimerState(true) }
                    initializeTimer()
                }
            } catch (e: Exception) {
                trySendEffect { SignupUIEffect.ShowMessage("Unable to send OTP code") }
            }
        }
    }

    private fun handleOnContinueButtonClickedEvent(
        activity: Activity,
        emailAddress: String,
        password: String,
        passwordRepeated: String,
        fullName: String
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

        val passwordRepeatedErrorText =
            if (!passwordRepeated.matches(activity.getString(R.string.password_regex).toRegex()))
                "Invalid password format"
            else
                ""

        val fullNameErrorText =
            if (!fullName.matches(activity.getString(R.string.name_regex).toRegex()))
                "Invalid name format"
            else
                ""

        updateState {
            fieldStateValidation(
                emailErrorText,
                fullNameErrorText,
                passwordErrorText,
                passwordRepeatedErrorText
            )
        }

        with(uiState.value) {

            if (emailAddressError.isEmpty() && passwordError.isEmpty() &&
                passwordRepeatedError.isEmpty() && fullNameError.isEmpty()
            ) {

                if (password == passwordRepeated) {

                    viewModelScope.launch {
                        updateState { loading() }
                        try {
                            checkExistenceUseCase.invoke(emailAddress) {
                                if (it == "success") {
                                    updateState {
                                        updateUserData(
                                            emailAddress,
                                            fullName,
                                            password,
                                            ""
                                        )
                                    }
                                    updateState { updateSuccess() }
                                } else {
                                    trySendEffect { SignupUIEffect.ShowMessage(it) }
                                }
                            }
                        } catch (e: Exception) {
                            trySendEffect { SignupUIEffect.ShowMessage("Something unexpected happened") }
                        } finally {
                            updateState { idle() }
                        }
                    }
                } else {
                    trySendEffect { SignupUIEffect.ShowMessage("Passwords don't match") }
                }
            }
        }

    }

    private fun handleOnCompleteButtonClickedEvent(enteredOpt: String) {
        if(enteredOpt == uiState.value.pinCode) {
            viewModelScope.launch {
                try {
                    addUserToRemoteUseCase(uiState.value.userData) {
                        if(it == "success") {
                            updateState { updateCompletionState() }
                        } else {
                            trySendEffect { SignupUIEffect.ShowMessage(it) }
                        }
                    }
                } catch (e: Exception) {
                    trySendEffect { SignupUIEffect.ShowMessage("Unable to register user") }
                }
            }
        } else {
            trySendEffect { SignupUIEffect.ShowMessage("Wrong pin code") }
        }
    }
}