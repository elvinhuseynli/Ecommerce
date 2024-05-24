package com.example.e_commerce.ui.fragments.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentContactDetailsBinding


class ContactDetailsFragment : Fragment() {

    private lateinit var binding: FragmentContactDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentContactDetailsBinding.inflate(layoutInflater)

        setWebView()

        return binding.root
    }

    private fun setWebView() = with(binding){
        webView.webViewClient = WebViewClient()
        webView.settings.setSupportZoom(true)
        webView.loadUrl("file:///android_asset/contact_details.html")
    }

}