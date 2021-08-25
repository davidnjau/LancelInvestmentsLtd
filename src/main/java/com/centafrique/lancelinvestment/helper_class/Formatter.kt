package com.centafrique.lancelinvestment.helper_class

import com.centafrique.lancelinvestment.entity.Products
import com.centafrique.lancelinvestment.repository.ProductsRepository
import kotlinx.coroutines.*

class Formatter {

    fun productDetails(productsRepository: ProductsRepository, products: Products)= runBlocking{
        getProductDetails(productsRepository, products)
    }

    suspend fun getProductDetails(productsRepository: ProductsRepository, products: Products): Products?{
        var productDetails = Products()

        val job = Job()
        CoroutineScope(Dispatchers.IO + job).launch {
            productDetails = productsRepository.save(products)
        }.join()
        return productDetails
    }

}