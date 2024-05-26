package com.example.e_commerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.e_commerce.R


data class LanguageData(
    val image: Int,
    val text: String
)

class SpinnerAdapter(
    private val context: Context,
    private val langList: List<LanguageData>
): BaseAdapter() {
    override fun getCount(): Int {
        return langList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_lang, parent, false)

        val tvLang = view.findViewById<TextView>(R.id.tvCountry)
        val ivLang = view.findViewById<ImageView>(R.id.ivCountry)

        tvLang.text = langList[position].text
        ivLang.setImageDrawable(AppCompatResources.getDrawable(context, langList[position].image))

        return view
    }
}