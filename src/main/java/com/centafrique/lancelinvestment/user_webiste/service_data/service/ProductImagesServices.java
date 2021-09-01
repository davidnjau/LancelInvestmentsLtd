package com.centafrique.lancelinvestment.user_webiste.service_data.service;

import com.centafrique.lancelinvestment.user_webiste.entity.ProductImages;

import java.util.List;

public interface ProductImagesServices {

    ProductImages addProductImages(ProductImages productImages);
    List<ProductImages> getAllProductImages(String productId);
}
