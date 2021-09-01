package com.centafrique.lancelinvestment.user_webiste.helper_class

import com.centafrique.lancelinvestment.user_webiste.entity.ProductImages
import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes
import javax.print.attribute.standard.PrintQuality

data class ProductDetails(

    val productId:String?,
    val productName: String,
    val productDescription: String,
    val productIngredients: String,

    val productSizes : List<ProductSizes>,
    val productImages : List<ProductImages>,
)

data class DynamicRes(
    var details: Any
)

data class DynamicFullRes(
    val int: Int,
    val nextPage: String?,
    val previousPage:String?,
    val results: List<ProductDetails>
)

data class AddToCart(
    val productId: String,
    val productSize: Double,
    val productQuantity: Int
)