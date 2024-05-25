package com.example.e_commerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.data.models.main.ProductDataModel
import com.example.e_commerce.databinding.ItemCartProductBinding
import com.example.e_commerce.databinding.ItemLocationBinding

data class CartProductModel(
    val productDetails: ProductDataModel,
    val amount: Int
)

class CartProductAdapter(
    private val context: Context,
    private val productList: List<CartProductModel>,
    private val callbackOnDec: (String)->Unit,
    private val callbackOnInc: (String)->Unit,
    private val callbackOnDelete: (String)->Unit
): RecyclerView.Adapter<CartProductAdapter.ViewHolder>() {

    private lateinit var binding: ItemCartProductBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setViews(position: Int) = with(binding) {
            val item = productList[position]

            price.text = context.getString(R.string.product_price, item.productDetails.price)
            productDesc.text = context.getString(
                R.string.product_desc,
                item.productDetails.producerCompany,
                item.productDetails.productName
            )
            tvAmount.text = item.amount.toString()
            productDesc.isSelected = true

            incrementBtn.setOnClickListener {
                callbackOnInc.invoke(item.productDetails.productId)
            }
            decrementBtn.setOnClickListener {
                callbackOnDec.invoke(item.productDetails.productId)
            }
            deleteBtn.setOnClickListener {
                callbackOnDelete.invoke(item.productDetails.productId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartProductAdapter.ViewHolder {
        binding = ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CartProductAdapter.ViewHolder, position: Int) {
        ViewHolder(binding.root).setViews(position)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}