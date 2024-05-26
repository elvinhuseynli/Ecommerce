package com.example.e_commerce.ui.fragments.main.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.findNavController
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.CustomSpinner
import com.example.e_commerce.core_utils.LocaleHelper
import com.example.e_commerce.core_utils.langList
import com.example.e_commerce.databinding.FragmentSettingsBinding
import com.example.e_commerce.ui.adapters.SpinnerAdapter
import com.yariksoffice.lingver.Lingver

class SettingsFragment : Fragment(), CustomSpinner.OnSpinnerEventsListener {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        val adapter = SpinnerAdapter(requireContext(), langList)

        binding.spinner.setSpinnerEventsListener(this)
        binding.spinner.adapter = adapter

        val currentLang = Lingver.getInstance().getLanguage()
        println(currentLang)
        when(currentLang) {
            "az"->binding.spinner.setSelection(0)
            "en"->binding.spinner.setSelection(1)
        }
    }

    private fun setListeners() = with(binding) {
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var lang = "en"
                when(position) {
                    0-> lang = "az"
                    1-> lang = "en"
                    2-> lang = "ru"
                }
                Lingver.getInstance().setLocale(requireContext(), lang)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    override fun onPopupWindowOpened(spinner: Spinner?) {
        binding.spinner.background = AppCompatResources.getDrawable(requireContext(), R.drawable.spinner_open_bg)
    }

    override fun onPopupWindowClosed(spinner: Spinner?) {
        binding.spinner.background = AppCompatResources.getDrawable(requireContext(), R.drawable.spinner_bg)
    }

}