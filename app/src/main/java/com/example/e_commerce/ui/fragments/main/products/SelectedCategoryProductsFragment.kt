package com.example.e_commerce.ui.fragments.main.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.MainApplication
import com.example.e_commerce.core_utils.CookieBarInst
import com.example.e_commerce.core_utils.viewLifecycleScope
import com.example.e_commerce.databinding.FragmentSelectedCategoryProductsBinding
import com.example.e_commerce.ui.adapters.ProductAdapter
import com.example.e_commerce.ui.intents.main.ProductUIEffect
import com.example.e_commerce.ui.intents.main.ProductUIEvent
import com.example.e_commerce.ui.intents.main.ProductUIState
import com.example.e_commerce.ui.viewModelFactory.ViewModelFactory
import com.example.e_commerce.ui.viewmodels.main.ProductViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import javax.inject.Inject

class SelectedCategoryProductsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentSelectedCategoryProductsBinding
    private val viewModel: ProductViewModel by viewModels { viewModelFactory }

    private var userId = ""
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectedCategoryProductsBinding.inflate(layoutInflater)

        (activity?.application as? MainApplication)?.getComponent()?.inject(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val category = arguments?.getString("category").orEmpty()

        userId = requireActivity().intent.extras?.getString("userId") ?: ""

        setListeners()

        viewModel.uiEffect.onSubscription {
            if(savedInstanceState==null) {
                viewModel.onEvent(ProductUIEvent.OnCategoryViewCreated(userId, category))
            }
        }.onEach(::renderEffect).launchIn(viewLifecycleScope)
        viewModel.uiState.onEach(::renderState).launchIn(viewLifecycleScope)
    }

    private fun setListeners() = with(binding) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchText = newText.orEmpty().lowercase()
                viewModel.onEvent(ProductUIEvent.OnSearchViewChange(searchText))
                return false
            }
        })
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun renderState(state: ProductUIState) = with(binding){

        progressBar.visibility = if(state.isLoading) View.VISIBLE else View.GONE
        gvProduct.visibility = if(state.isLoading) View.GONE else View.VISIBLE

        state.data.let {
            adapter = ProductAdapter(requireContext(), state.updatedList, state.favoritesList, {
                viewModel.onEvent(ProductUIEvent.OnAddButtonClicked(userId, it))
            }) {
                viewModel.onEvent(ProductUIEvent.OnFavButtonClicked(userId, it))
                updateAdapter()
            }
            gvProduct.adapter = adapter
        }
    }

    private fun updateAdapter() {
        adapter.notifyDataSetChanged()
    }

    private fun renderEffect(effect: ProductUIEffect) {
        when(effect) {
            is ProductUIEffect.ShowMessage -> handleShowMessageEffect(effect.msg)
        }
    }

    private fun handleShowMessageEffect(msg: String) {
        CookieBarInst.buildMessage(requireActivity(), msg)
    }
}