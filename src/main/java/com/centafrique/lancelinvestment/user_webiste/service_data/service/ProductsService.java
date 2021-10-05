package com.centafrique.lancelinvestment.user_webiste.service_data.service;

import com.centafrique.lancelinvestment.user_webiste.entity.Products;

import java.util.List;

public interface ProductsService {

    Products addProduct(Products products);
    Products getProduct(String productId);
    List<Products> getProductsList (int pageNo, int pageSize, String sortField, String sortDirection);

}
