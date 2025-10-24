package com.fashionstore.repository;

import com.fashionstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * OrderItem Repository - Interface cho các thao tác CRUD với OrderItem
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Lấy tất cả items của một đơn hàng
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * Lấy tất cả order items của một sản phẩm
     */
    List<OrderItem> findByProductId(Long productId);
}
