package com.example.e_commerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.data.models.main.LocationDataModel
import com.example.e_commerce.databinding.ItemLocationBinding

class LocationAdapter(
    private val context: Context,
    private val locationList: List<LocationDataModel>,
    private val callbackOnClick: (Int)->Unit
): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private lateinit var binding: ItemLocationBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setViews(position: Int) = with(binding) {
            val item = locationList[position]

            tvLocation.text = context.getString(R.string.location, item.id, item.addressTitle)

            layout.setOnClickListener {
                callbackOnClick.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        binding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, position: Int) {
        ViewHolder(binding.root).setViews(position)
    }

    override fun getItemCount(): Int {
        return locationList.size
    }
}