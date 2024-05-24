package com.example.e_commerce.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.databinding.ItemCampaignBinding


data class CampaignsModel(
    val imgPath: String,
    val title: String,
    val dateRange: String
)

class CampaignsAdapter(
    private val itemList: List<CampaignsModel>,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<CampaignsAdapter.ViewHolder>() {

    private lateinit var binding: ItemCampaignBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setViews(position: Int) = with(binding) {
            val item = itemList[position]

            tvTitle.text = item.title
            tvDate.text = item.dateRange
            tvTitle.isSelected = true
            tvDate.isSelected = true
            Glide.with(itemView).load(item.imgPath).into(ivCampaign)
            campaignContainer.setOnClickListener {
                callback.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignsAdapter.ViewHolder {
        binding = ItemCampaignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CampaignsAdapter.ViewHolder, position: Int) {
        ViewHolder(binding.root).setViews(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}