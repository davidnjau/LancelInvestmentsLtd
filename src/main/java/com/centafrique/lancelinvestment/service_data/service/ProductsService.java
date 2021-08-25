package com.centafrique.lancelinvestment.service_data.service;

import com.centafrique.lancelinvestment.entity.Products;

import java.util.Date;
import java.util.List;

public interface ProductsService {

    Products addProduct(Products products);
    Products getProduct(String productId);
    List<Products> getProductsList (int pageNo, int pageSize, String sortField, String sortDirection);

}
