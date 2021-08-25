package com.centafrique.lancelinvestment.helper_class

import com.centafrique.lancelinvestment.entity.ProductImages
import com.centafrique.lancelinvestment.entity.ProductSizes

data class ProductDetails(
    val productName: String,
    val productDescription: String,
    val productIngredients: String,

    val productSizes : List<ProductSizes>,
    val productImages : List<ProductImages>,
)

data class DynamicRes(
    var details: Any
)
