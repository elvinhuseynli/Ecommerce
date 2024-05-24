package com.example.e_commerce.ui.fragments.main.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.listOfProducts
import com.example.e_commerce.databinding.FragmentProductsBinding
import com.example.e_commerce.ui.adapters.ProductsCategoryAdapter

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductsBinding.inflate(layoutInflater)

        initView()

        return binding.root
    }

    private fun initView() = with(binding) {
        gvProduct.adapter = ProductsCategoryAdapter(requireContext(), listOfProducts) {
            val bundle = Bundle()
            bundle.putString("category", it)

            findNavController().navigate(R.id.action_productsFragment_to_selectedCategoryProductsFragment, bundle)
        }

        seeAllBtn.setOnClickListener {
            findNavController().navigate(R.id.action_productsFragment_to_allProductsFragment)
        }

    }

}