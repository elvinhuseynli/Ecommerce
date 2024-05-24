package com.example.e_commerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.e_commerce.databinding.ItemLangBinding


data class LanguageData(
    val image: Int,
    val text: String
)

class LanguageAdapter(context: Context) : ArrayAdapter<LanguageData>(context, 0) {

    private lateinit var binding: ItemLangBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if(convertView == null) {
            binding = ItemLangBinding.inflate(LayoutInflater.from(context), parent, false)

            view = binding.root
        } else {
            view = convertView
        }

        getItem(position)?.let { lang ->
            setItem(view, lang)
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getDropDownView(position, convertView, parent)
    }

    override fun getItem(position: Int): LanguageData? {
        return super.getItem(position)
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun isEnabled(position: Int): Boolean {
        return super.isEnabled(position)
    }

    private fun setItem(view: View, lang: LanguageData) {

    }
}