package com.fashionstore.repository;

import com.fashionstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Order Repository - Interface cho các thao tác CRUD với Order
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Lấy tất cả đơn hàng của user
     */
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    /**
     * Lấy đơn hàng theo status
     */
    List<Order> findByStatus(String status);

    /**
     * Lấy đơn hàng của user theo status
     */
    List<Order> findByUserIdAndStatus(Long userId, String status);

    /**
     * Lấy 5 đơn hàng mới nhất (cho admin dashboard)
     */
    List<Order> findTop5ByOrderByCreatedAtDesc();

    /**
     * Đếm tổng số đơn hàng
     */
    long count();

    /**
     * Tính tổng doanh thu từ các đơn hàng đã hoàn thành
     */
    @Query("SELECT COALESCE(SUM(o.totalAmount), 0) FROM Order o WHERE o.status = :status")
    BigDecimal sumTotalAmountByStatus(@Param("status") String status);

    /**
     * Đếm số đơn hàng theo status
     */
    long countByStatus(String status);
}
