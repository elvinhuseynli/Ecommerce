package com.example.e_commerce.ui.fragments.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentPrivacyPolicyBinding


class PrivacyPolicyFragment : Fragment() {

    private lateinit var binding: FragmentPrivacyPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPrivacyPolicyBinding.inflate(layoutInflater)

        setWebView()

        return binding.root
    }

    private fun setWebView() = with(binding){
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.loadUrl("file:///android_asset/privacy_policy.html")
    }

}