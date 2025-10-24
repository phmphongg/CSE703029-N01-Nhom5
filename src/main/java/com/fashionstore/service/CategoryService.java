package com.fashionstore.service;

import com.fashionstore.model.Category;
import com.fashionstore.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Category Service - Business logic cho Category
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Lấy tất cả categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Lấy category theo ID
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Lấy category theo tên
     */
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Tạo category mới
     */
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Danh mục đã tồn tại!");
        }
        return categoryRepository.save(category);
    }

    /**
     * Cập nhật category
     */
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Xóa category
     */
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
