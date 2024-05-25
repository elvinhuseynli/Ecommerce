package com.example.e_commerce.domain.usecases.main

import com.example.e_commerce.data.models.main.ProductDataModel
import com.example.e_commerce.domain.repositories.main.ProductRepository
import javax.inject.Inject

class FetchProductsByCategoryUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(
        category: String,
        callback: (String)->Unit,
        callbackData: (List<ProductDataModel>) -> Unit
    ) {
        repository.fetchProductsByCategory(category).addOnCompleteListener {
            if(it.isSuccessful) {
                if(!it.result.isEmpty) {
                    val data = arrayListOf<ProductDataModel>()
                    for(doc in it.result) {
                        data.add(
                            ProductDataModel(
                                doc.id,
                                doc.getString("category").orEmpty(),
                                doc.getString("price").orEmpty(),
                                doc.getString("producerCompany").orEmpty(),
                                doc.getString("productName").orEmpty()
                            )
                        )
                    }
                    callback.invoke("success")
                    callbackData.invoke(data)
                } else {
                    callback.invoke("No data available")
                }
            } else {
                callback.invoke("Server error occurred")
            }
        }
    }
}