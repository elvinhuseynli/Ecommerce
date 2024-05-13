package com.example.e_commerce.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.MainApplication
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.CookieBarInst
import com.example.e_commerce.core_utils.MailAPI
import com.example.e_commerce.core_utils.viewLifecycleScope
import com.example.e_commerce.databinding.FragmentSignupBinding
import com.example.e_commerce.ui.intents.auth.SignupUIEffect
import com.example.e_commerce.ui.intents.auth.SignupUIEvent
import com.example.e_commerce.ui.intents.auth.SignupUIState
import com.example.e_commerce.ui.reducers.auth.SignupReducer.resetSuccess
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.auth.SignupViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SignupFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SignupViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignupBinding.inflate(layoutInflater)

        (activity?.application as? MainApplication)?.getComponent()?.inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        viewModel.uiEffect.onEach(::renderEffect).launchIn(viewLifecycleScope)
        viewModel.uiState.onEach(::renderState).launchIn(viewLifecycleScope)
    }

    private fun setListeners() = with(binding) {
        nextButton.setOnClickListener {
            viewModel.onEvent(
                SignupUIEvent.OnContinueButtonClicked(
                    requireActivity(),
                    emailInput.text.toString(),
                    passwordInput.text.toString(),
                    passwordRepeatedInput.text.toString(),
                    fullNameInput.text.toString()
                )
            )
        }

        loginButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun renderState(state: SignupUIState) = with(binding) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        nextButton.text = if (state.isLoading) "" else "Next"
        emailInput.error = state.emailAddressError.ifEmpty { null }
        passwordInput.error = state.passwordError.ifEmpty { null }
        passwordRepeatedInput.error = state.passwordRepeatedError.ifEmpty { null }
        fullNameInput.error = state.fullNameError.ifEmpty { null }

        if (state.isSuccessful) {
            emailInput.text.clear()
            passwordInput.text.clear()
            passwordRepeatedInput.text.clear()
            fullNameInput.text.clear()

            findNavController().navigate(R.id.action_signupFragment_to_emailValidationFragment)
            viewModel.onEvent(SignupUIEvent.OnNavigated)
        }
    }

    private fun renderEffect(effect: SignupUIEffect) {
        when (effect) {
            is SignupUIEffect.ShowMessage -> handleShowMessageEffect(effect.msg)
        }
    }

    private fun handleShowMessageEffect(msg: String) {
        CookieBarInst.buildMessage(requireActivity(), msg)
    }

}