package com.centafrique.lancelinvestment.user_webiste.service_data.service;

import com.centafrique.lancelinvestment.user_webiste.entity.CartItems;

import java.util.List;

public interface CartItemsService {

    CartItems addCart(CartItems cartItems);
    List<CartItems> getCartItems(String userId);


}
