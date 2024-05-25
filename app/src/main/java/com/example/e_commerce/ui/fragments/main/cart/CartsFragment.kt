package com.example.e_commerce.ui.fragments.main.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.e_commerce.MainApplication
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.CookieBarInst
import com.example.e_commerce.core_utils.viewLifecycleScope
import com.example.e_commerce.databinding.FragmentCartsBinding
import com.example.e_commerce.ui.adapters.CartProductAdapter
import com.example.e_commerce.ui.adapters.ProductAdapter
import com.example.e_commerce.ui.intents.main.CartUIEffect
import com.example.e_commerce.ui.intents.main.CartUIEvent
import com.example.e_commerce.ui.intents.main.CartUIState
import com.example.e_commerce.ui.intents.main.ProductUIEffect
import com.example.e_commerce.ui.intents.main.ProductUIEvent
import com.example.e_commerce.ui.intents.main.ProductUIState
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.main.CartProductViewModel
import com.example.e_commerce.ui.viewmodels.main.ProductViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import javax.inject.Inject

class CartsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentCartsBinding
    private val viewModel: CartProductViewModel by viewModels { viewModelFactory }

    private var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartsBinding.inflate(layoutInflater)

        (activity?.application as? MainApplication)?.getComponent()?.inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = requireActivity().intent.extras?.getString("userId") ?: ""

//        setListeners()

        viewModel.uiEffect.onSubscription {
            if(savedInstanceState==null) {
                viewModel.onEvent(CartUIEvent.OnViewCreated(userId))
            }
        }.onEach(::renderEffect).launchIn(viewLifecycleScope)
        viewModel.uiState.onEach(::renderState).launchIn(viewLifecycleScope)
    }


    private fun renderState(state: CartUIState) = with(binding){

        progressBar.visibility = if(state.isLoading) View.VISIBLE else View.GONE
        rvCart.visibility = if(state.isLoading) View.GONE else View.VISIBLE
        totalPrice.text = getString(R.string.product_price, "%.2f".format(state.totalPrice))

        state.data.let {
            rvCart.adapter = CartProductAdapter(requireContext(), state.data, {
                viewModel.onEvent(CartUIEvent.OnDecrementButtonClicked(userId, it))
            },{
                viewModel.onEvent(CartUIEvent.OnIncrementButtonClicked(userId, it))
            }) {
                viewModel.onEvent(CartUIEvent.OnDeleteButtonClicked(userId, it))
            }
        }
    }

    private fun renderEffect(effect: CartUIEffect) {
        when(effect) {
            is CartUIEffect.ShowMessage -> handleShowMessageEffect(effect.msg)
        }
    }

    private fun handleShowMessageEffect(msg: String) {
        CookieBarInst.buildMessage(requireActivity(), msg)
    }

}