package com.example.e_commerce.ui.fragments.main.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentCartsBinding

class CartsFragment : Fragment() {

    private lateinit var binding: FragmentCartsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartsBinding.inflate(layoutInflater)

        return binding.root
    }

}