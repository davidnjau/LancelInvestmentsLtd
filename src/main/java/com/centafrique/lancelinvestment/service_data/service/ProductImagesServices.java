package com.centafrique.lancelinvestment.service_data.service;

import com.centafrique.lancelinvestment.entity.ProductImages;

import java.util.List;

public interface ProductImagesServices {

    ProductImages addProductImages(ProductImages productImages);
    List<ProductImages> getAllProductImages(String productId);
}
