package com.centafrique.lancelinvestment.user_webiste.service_data.service_impl;

import com.centafrique.lancelinvestment.user_webiste.entity.CartItems;
import com.centafrique.lancelinvestment.user_webiste.entity.ProductSizes;
import com.centafrique.lancelinvestment.user_webiste.helper_class.*;
import com.centafrique.lancelinvestment.user_webiste.repository.CartItemsRepository;
import com.centafrique.lancelinvestment.user_webiste.service_data.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemsServiceImpl implements CartItemsService {

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductsServiceImpl productsServiceImpl;

    @Override
    public CartItems addCart(CartItems cartItems) {

        Optional<CartItems> cartItemsOptional = cartItemsRepository.
                findAllByProductIdAndUserId(cartItems.getProductId(), cartItems.getUserId());

        if (cartItemsOptional.isPresent()){

            CartItems oldCartItems = cartItemsOptional.get();
            oldCartItems.setProductId(cartItems.getProductId());
            oldCartItems.setProductPrice(cartItems.getProductPrice());
            oldCartItems.setUserId(cartItems.getUserId());
            oldCartItems.setProductSize(cartItems.getProductSize());
            oldCartItems.setProductQuantity(cartItems.getProductQuantity());
            return cartItemsRepository.save(oldCartItems);

        }else {
            return cartItemsRepository.save(cartItems);

        }
    }

    @Override
    public List<CartItems> getCartItems(String userId) {
        return cartItemsRepository.findAllByUserId(userId);
    }

    public DynamicAnyRes getMyCartItems(String userId){

        List<CartDetails> cartDetailsList = new ArrayList<>();

        List<CartItems> cartItemsList = getCartItems(userId);
        for (int i = 0; i < cartItemsList.size(); i++){

            String productId = cartItemsList.get(i).getProductId();
            double productSize = cartItemsList.get(i).getProductSize();
            double productQuantity = cartItemsList.get(i).getProductQuantity();
            double productPrice = cartItemsList.get(i).getProductPrice();

            ProductDetails productDetails = productsServiceImpl.getProductDetails(productId);

            CartData addToCart = new CartData(productPrice, productSize, productQuantity);

            CartDetails cartDetails = new CartDetails(productDetails, addToCart);
            cartDetailsList.add(cartDetails);
        }



        return new DynamicAnyRes(
                cartDetailsList.size(),
                null, null,
                cartDetailsList
        );

    }
    public DynamicAnyRes getAllCartItems(){

        List<CartDetails> cartDetailsList = new ArrayList<>();

        List<CartItems> cartItemsList = cartItemsRepository.findAll();
        for (int i = 0; i < cartItemsList.size(); i++){

            String productId = cartItemsList.get(i).getProductId();
            double productSize = cartItemsList.get(i).getProductSize();
            double productQuantity = cartItemsList.get(i).getProductQuantity();
            double productPrice = cartItemsList.get(i).getProductPrice();

            ProductDetails productDetails = productsServiceImpl.getProductDetails(productId);

            CartData addToCart = new CartData(productPrice, productSize, productQuantity);

            CartDetails cartDetails = new CartDetails(productDetails, addToCart);
            cartDetailsList.add(cartDetails);
        }



        return new DynamicAnyRes(
                cartDetailsList.size(),
                null, null,
                cartDetailsList
        );

    }

}
