package com.fashionstore.repository;

import com.fashionstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Product Repository - Interface cho các thao tác CRUD với Product
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Tìm sản phẩm theo category
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Tìm sản phẩm theo tên (like search)
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Tìm sản phẩm trong khoảng giá
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Tìm sản phẩm theo category và khoảng giá
     */
    List<Product> findByCategoryIdAndPriceBetween(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Lấy sản phẩm mới nhất (8 sản phẩm)
     */
    List<Product> findTop8ByOrderByCreatedAtDesc();

    /**
     * Tìm kiếm sản phẩm với nhiều điều kiện
     */
    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(
        @Param("categoryId") Long categoryId,
        @Param("search") String search,
        @Param("minPrice") BigDecimal minPrice,
        @Param("maxPrice") BigDecimal maxPrice
    );

    /**
     * Lấy sản phẩm liên quan (cùng category, không bao gồm sản phẩm hiện tại)
     */
    List<Product> findTop4ByCategoryIdAndIdNot(Long categoryId, Long productId);
}
