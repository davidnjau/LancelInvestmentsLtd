package com.centafrique.lancelinvestment.user_webiste.repository;

import com.centafrique.lancelinvestment.user_webiste.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemsRepository extends JpaRepository<CartItems, String> {

    Optional<CartItems> findAllByProductIdAndUserId(String productId, String userId);
    List<CartItems> findAllByUserId(String userId);

}
