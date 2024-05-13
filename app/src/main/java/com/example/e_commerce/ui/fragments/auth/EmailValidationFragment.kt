package com.example.e_commerce.ui.fragments.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.MainApplication
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.CookieBarInst
import com.example.e_commerce.core_utils.viewLifecycleScope
import com.example.e_commerce.databinding.FragmentEmailValidationBinding
import com.example.e_commerce.ui.intents.auth.SignupUIEffect
import com.example.e_commerce.ui.intents.auth.SignupUIEvent
import com.example.e_commerce.ui.intents.auth.SignupUIState
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.auth.SignupViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import javax.inject.Inject

class EmailValidationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentEmailValidationBinding
    private val viewModel: SignupViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmailValidationBinding.inflate(layoutInflater)

        (activity?.application as? MainApplication)?.getComponent()?.inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        viewModel.uiEffect.onSubscription {
            viewModel.onEvent(
                SignupUIEvent.OnViewCreated
            )
        }.onEach(::renderEffect).launchIn(viewLifecycleScope)
        viewModel.uiState.onEach(::renderState).launchIn(viewLifecycleScope)
    }

    private fun setListeners() = with(binding) {
        otpInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){}

            override fun afterTextChanged(s: Editable?) { confirmButton.isEnabled = s?.length == 4 }
        })

        confirmButton.setOnClickListener {
            viewModel.onEvent(SignupUIEvent.OnCompleteButtonClicked(otpInput.text.toString()))
        }
    }

    private fun renderState(state: SignupUIState) = with(binding) {
        if (!state.stateChanged) {
            sendButton.setOnClickListener {
                viewModel.onEvent(SignupUIEvent.OnViewCreated)
            }
        }

        sendButton.text = if (!state.stateChanged) "Send again" else state.timeLeft

        if(state.isComplete) {
            findNavController().navigate(R.id.action_emailValidationFragment_to_loginFragment)
        }
    }

    private fun renderEffect(effect: SignupUIEffect) {
        when(effect) {
            is SignupUIEffect.ShowMessage -> handleShowMessageEffect(effect.msg)
        }
    }

    private fun handleShowMessageEffect(msg: String) {
        CookieBarInst.buildMessage(requireActivity(), msg)
    }

}