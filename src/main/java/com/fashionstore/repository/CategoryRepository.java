package com.fashionstore.repository;

import com.fashionstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Category Repository - Interface cho các thao tác CRUD với Category
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Tìm category theo tên
     */
    Optional<Category> findByName(String name);

    /**
     * Kiểm tra category name đã tồn tại chưa
     */
    boolean existsByName(String name);
}
