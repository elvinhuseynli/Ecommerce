package com.example.e_commerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.e_commerce.R

data class ProductCategoryModel(
    val img: Int,
    val title: String,
)

class ProductsCategoryAdapter(
    context: Context,
    productsList: ArrayList<ProductCategoryModel>,
    private val callback: (String) -> Unit
) :ArrayAdapter<ProductCategoryModel>(context, 0, productsList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView?:
            LayoutInflater.from(context).inflate(R.layout.product_category_item, parent, false)

        val prodModel: ProductCategoryModel = getItem(position)?: ProductCategoryModel(0,"")
        val tvProduct = view.findViewById<TextView>(R.id.tvProduct)
        val ivProduct = view.findViewById<ImageView>(R.id.ivProduct)

        tvProduct.text = prodModel.title
        tvProduct.isSelected = true
        ivProduct.setImageDrawable(AppCompatResources.getDrawable(context, prodModel.img))

        val layout = view.findViewById<LinearLayout>(R.id.layout)

        layout.setOnClickListener {
            callback.invoke(prodModel.title)
        }

        return view
    }
}