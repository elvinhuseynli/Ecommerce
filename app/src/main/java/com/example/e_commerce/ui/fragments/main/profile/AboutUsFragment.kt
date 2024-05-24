package com.example.e_commerce.ui.fragments.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentAboutUsBinding


class AboutUsFragment : Fragment() {

    private lateinit var binding: FragmentAboutUsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAboutUsBinding.inflate(layoutInflater)

        setWebView()

        return binding.root

    }

    private fun setWebView() = with(binding) {
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.loadUrl("file:///android_asset/about_us.html")
    }
}