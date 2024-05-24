package com.example.e_commerce.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.e_commerce.databinding.ItemStoryBinding
import java.net.URI
import java.util.ArrayList

data class StoriesModel(
    val image: Int,
    val link: String
)

class StoriesAdapter(
    private val itemList: List<StoriesModel>,
    private val callback: (Int) -> Unit
) : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    private lateinit var binding: ItemStoryBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setViews(position: Int) = with(binding) {
            val item = itemList[position]

            ivStory.setImageResource(item.image)
            storyContainer.setOnClickListener {
                callback.invoke(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesAdapter.ViewHolder {
        binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: StoriesAdapter.ViewHolder, position: Int) {
        ViewHolder(binding.root).setViews(position)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}