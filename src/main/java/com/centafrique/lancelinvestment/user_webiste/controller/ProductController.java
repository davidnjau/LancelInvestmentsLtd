package com.centafrique.lancelinvestment.user_webiste.controller;

import com.centafrique.lancelinvestment.user_webiste.helper_class.*;
import com.centafrique.lancelinvestment.user_webiste.service_data.service_impl.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/api/v1/products/get_products", method = RequestMethod.GET)
    public ResponseEntity getProducts(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "9") Integer pageSize,
            @RequestParam(defaultValue = "createdAt") String sortField,
            @RequestParam(defaultValue = "DESC") String sortDirection){

        DynamicFullRes dynamicFullRes = productsServiceImpl.getPaginatedProducts(pageNo, pageSize, sortField, sortDirection);
        if (dynamicFullRes != null){
            return new ResponseEntity(dynamicFullRes, HttpStatus.OK);
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("There was an issue. Try again"));
        }

    }

    @RequestMapping(value = "/api/v1/user/products/add-to-cart", method = RequestMethod.POST)
    public ResponseEntity addProductCart(@RequestBody AddToCart addToCart){

        ResponseData response = productsServiceImpl.addToCart(addToCart);
        int statusCode = response.getStatusCode();
        String message = response.getMessage();

        if (statusCode == 200){
            return new ResponseEntity(message, HttpStatus.OK);
        }else if (statusCode == 400){
            return ResponseEntity.badRequest().body(new DynamicRes(message));
        }else if (statusCode == 403){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DynamicRes(message));
        }else {
            return ResponseEntity.badRequest().body(new DynamicRes("There was an issue. Try again"));
        }

    }

//    @RequestMapping(value = "shop-details/{productId}", method = RequestMethod.GET)
//    public ResponseEntity getProductDetail(@PathVariable("productId") String productId){
//
//        ProductDetails productDetails = productsServiceImpl.getProductDetails(productId);
//        if (productDetails != null){
//            return new ResponseEntity(productDetails, HttpStatus.OK);
//        }else {
//            return ResponseEntity.badRequest().body(new DynamicRes("There was an issue. Try again"));
//        }
//
//    }


}
