package com.centafrique.lancelinvestment.service_data.service;

import com.centafrique.lancelinvestment.entity.ProductSizes;

import java.util.List;

public interface ProductSizesInfo {

    ProductSizes addProductSizes(ProductSizes productSizes);
    List<ProductSizes> getProductSizes(String productId);


}
