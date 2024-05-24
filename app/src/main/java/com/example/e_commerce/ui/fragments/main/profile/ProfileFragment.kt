package com.example.e_commerce.ui.fragments.main.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentProfileBinding
import com.example.e_commerce.ui.activities.AuthActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)

        setListeners()

        return binding.root
    }

    private fun setListeners() = with(binding) {
        changePassBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_changePasswordFragment)
        }
        settingsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }
        favProdsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favoriteProductsFragment)
        }
        contactBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_contactDetailsFragment)
        }
        termsBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_privacyPolicyFragment)
        }
        aboutBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_aboutUsFragment)
        }
        logout.setOnClickListener {
            logoutFromAccount()
        }
    }

    private fun logoutFromAccount() {
        val sharedPreferences = requireActivity().getSharedPreferences("app", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("userId").apply()
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}