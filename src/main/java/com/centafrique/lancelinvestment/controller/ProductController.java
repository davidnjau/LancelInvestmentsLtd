package com.centafrique.lancelinvestment.controller;

import com.centafrique.lancelinvestment.helper_class.DynamicRes;
import com.centafrique.lancelinvestment.helper_class.ProductDetails;
import com.centafrique.lancelinvestment.service_data.service_impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @RequestMapping(value = "/api/v1/products/add_products", method = RequestMethod.POST)
    public ResponseEntity addProducts(@RequestBody ProductDetails productDetails){

        ProductDetails respProductDetails = productsServiceImpl.addProductDetails(productDetails);
        if (respProductDetails != null){
            return new ResponseEntity(respProductDetails, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("Product could not be added. Please try again."));
        }

    }


}
