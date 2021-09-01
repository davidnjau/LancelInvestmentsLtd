package com.centafrique.lancelinvestment.user_webiste.service_data.service;

import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes;

import java.util.List;

public interface ProductSizesInfo {

    ProductSizes addProductSizes(ProductSizes productSizes);
    List<ProductSizes> getProductSizes(String productId);
    ProductSizes getProductSize(String productId, double productSize);

}
