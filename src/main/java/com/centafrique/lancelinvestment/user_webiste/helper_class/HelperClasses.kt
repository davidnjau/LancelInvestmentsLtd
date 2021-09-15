package com.centafrique.lancelinvestment.user_webiste.helper_class

import com.centafrique.lancelinvestment.user_webiste.entity.ProductImages
import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes
import org.springframework.web.multipart.MultipartFile
import javax.print.attribute.standard.PrintQuality


data class ProductDataDetails(
    val productName: String,
    val productDescription: String,
    val productIngredients: String,
)

data class ProductDisplay(

    val index: String,
    val productId:String?,
    val productName: String,
    val sizeAmount: String,
    val productPrice: String,

)

data class UploadProduct(
    val productId:String?,
    val productName: String,
    val productDescription: String,
    val productIngredients: String,

    val productSizes : List<ProductSizes>,
    val fileList : List<FileImages>
)

data class ProductDetails(

    val productId:String?,
    val productName: String,
    val productDescription: String,
    val productIngredients: String,

    val productSizes : List<ProductSizes>,
    val productImages : List<ProductImages>
)

data class FileImages(
    val image1: MultipartFile,
    val image2: MultipartFile,
    val image3: MultipartFile
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
data class DynamicAnyRes(
    val count: Int,
    val nextPage: String?,
    val previousPage:String?,
    val results: List<CartDetails>
)

data class CartDetails(
    val productDetails: ProductDetails,
    val cartData: CartData
)

data class CartData(
    val productPrice: Double,
    val productQuantity: Double,
    val productSize: Double)

data class AddToCart(
    val productId: String,
    val productSize: Double,
    val productQuantity: Double
)

data class ResponseData(
    val statusCode: Int,
    val message: String
)