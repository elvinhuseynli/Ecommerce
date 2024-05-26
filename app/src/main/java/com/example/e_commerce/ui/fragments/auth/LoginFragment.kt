package com.example.e_commerce.ui.fragments.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.MainApplication
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.CookieBarInst
import com.example.e_commerce.core_utils.viewLifecycleScope
import com.example.e_commerce.databinding.FragmentLoginBinding
import com.example.e_commerce.ui.activities.MainActivity
import com.example.e_commerce.ui.intents.auth.LoginUIEffect
import com.example.e_commerce.ui.intents.auth.LoginUIEvent
import com.example.e_commerce.ui.intents.auth.LoginUIState
import com.example.e_commerce.ui.reducers.auth.LoginReducer
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.auth.LoginViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import javax.inject.Inject

class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        (activity?.application as? MainApplication)?.getComponent()?.inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        viewModel.uiState.onEach(::renderState).launchIn(viewLifecycleScope)

        viewModel.uiEffect.onEach(::renderEffect).launchIn(viewLifecycleScope)
    }

    private fun setListeners() = with(binding) {
        loginButton.setOnClickListener {
            viewModel.onEvent(
                LoginUIEvent.OnLoginButtonClicked(
                    requireActivity(), emailInput.text.toString(), passwordInput.text.toString()
                )
            )
        }

        signupButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun renderState(state: LoginUIState) = with(binding) {
        progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        loginButton.text = if (state.isLoading) "" else getString(R.string.log_in_title)
        emailInput.error = state.emailAddressError.ifEmpty { null }
        passwordInput.error = state.passwordError.ifEmpty { null }

        if (state.isSuccessful) {
            viewModel.onEvent(
                LoginUIEvent.OnCreate(requireActivity())
            )
            emailInput.text.clear()
            passwordInput.text.clear()
            changeActivity(state.userId)
        }
    }

    private fun changeActivity(userId: String) {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }

    private fun renderEffect(effect: LoginUIEffect) {
        when (effect) {
            is LoginUIEffect.ShowMessage -> handleShowMessageEffect(effect.msg)
        }
    }

    private fun handleShowMessageEffect(msg: String) {
        CookieBarInst.buildMessage(requireActivity(), msg)
    }
}