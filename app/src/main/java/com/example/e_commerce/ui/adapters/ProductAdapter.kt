package com.example.e_commerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.e_commerce.R
import com.example.e_commerce.data.models.main.ProductDataModel

class ProductAdapter(
    context: Context,
    productList: List<ProductDataModel>,
    private val favoritesList: ArrayList<String>,
    private val callbackAdd: (String) -> Unit,
    private val callbackFav: (String) -> Unit
) : ArrayAdapter<ProductDataModel>(context, 0, productList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_product, parent, false)

        val prodModel: ProductDataModel = getItem(position) ?: ProductDataModel("", "", "", "", "")
        val price = view.findViewById<TextView>(R.id.price)
        val productDesc = view.findViewById<TextView>(R.id.productDesc)

        price.text = context.getString(R.string.product_price, prodModel.price)
        productDesc.text = context.getString(
            R.string.product_desc,
            prodModel.producerCompany,
            prodModel.productName
        )
        productDesc.isSelected = true

        val favBtn = view.findViewById<ImageView>(R.id.favBtn)
        val addBtn = view.findViewById<Button>(R.id.addBtn)
        if(prodModel.productId in favoritesList) {
            favBtn.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.favorite_checked))
        } else {
            favBtn.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.favorite_unchecked))
        }

        favBtn.setOnClickListener {
            callbackFav.invoke(prodModel.productId)
        }
        addBtn.setOnClickListener {
            callbackAdd.invoke(prodModel.productId)
        }

        return view
    }
}