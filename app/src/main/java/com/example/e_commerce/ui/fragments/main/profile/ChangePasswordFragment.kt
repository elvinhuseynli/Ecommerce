package com.example.e_commerce.ui.fragments.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.MainApplication
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.CookieBarInst
import com.example.e_commerce.core_utils.viewLifecycleScope
import com.example.e_commerce.databinding.FragmentChangePasswordBinding
import com.example.e_commerce.ui.intents.auth.SignupUIEffect
import com.example.e_commerce.ui.intents.main.ChangePassUIEffect
import com.example.e_commerce.ui.intents.main.ChangePassUIEvent
import com.example.e_commerce.ui.intents.main.ChangePassUIState
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.main.ChangePasswordViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ChangePasswordFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentChangePasswordBinding
    private val viewModel: ChangePasswordViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChangePasswordBinding.inflate(layoutInflater)

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

        val userId = requireActivity().intent.extras?.getString("userId") ?: ""

        updateBtn.setOnClickListener {
            viewModel.onEvent(
                ChangePassUIEvent.OnUpdateButtonClicked(
                    requireActivity(),
                    userId,
                    password.text.toString(),
                    passwordRepeated.text.toString(),
                )
            )
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun renderState(state: ChangePassUIState) = with(binding){

        password.error = state.passwordError.ifEmpty { null }
        passwordRepeated.error = state.passwordRepeatedError.ifEmpty { null }

        if(state.isSuccessful) {
            password.text.clear()
            passwordRepeated.text.clear()

            findNavController().popBackStack()
        }
    }

    private fun renderEffect(effect: ChangePassUIEffect) {
        when (effect) {
            is ChangePassUIEffect.ShowMessage -> handleShowMessageEffect(effect.msg)
        }
    }

    private fun handleShowMessageEffect(msg: String) {
        CookieBarInst.buildMessage(requireActivity(), msg)
    }
}