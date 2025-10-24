package com.fashionstore.repository;

import com.fashionstore.model.Cart;
import com.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Cart Repository - Interface cho các thao tác CRUD với Cart
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Lấy tất cả items trong giỏ hàng của user
     */
    List<Cart> findByUserId(Long userId);

    /**
     * Lấy một cart item cụ thể của user với product, size và color
     */
    Optional<Cart> findByUserIdAndProductIdAndSizeAndColor(
        Long userId, 
        Long productId, 
        String size, 
        String color
    );

    /**
     * Xóa tất cả items trong giỏ hàng của user
     */
    void deleteByUserId(Long userId);

    /**
     * Xóa tất cả items của user với một product cụ thể
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);

    /**
     * Đếm số lượng items trong giỏ hàng của user
     */
    long countByUserId(Long userId);
}
