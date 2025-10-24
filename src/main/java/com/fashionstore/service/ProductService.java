package com.fashionstore.service;

import com.fashionstore.model.Product;
import com.fashionstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Product Service - Business logic cho Product
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Lấy tất cả sản phẩm
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Lấy sản phẩm theo ID
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Lấy 8 sản phẩm mới nhất cho trang chủ
     */
    public List<Product> getLatestProducts() {
        return productRepository.findTop8ByOrderByCreatedAtDesc();
    }

    /**
     * Tìm kiếm sản phẩm với nhiều điều kiện
     */
    public List<Product> searchProducts(Long categoryId, String search, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.searchProducts(categoryId, search, minPrice, maxPrice);
    }

    /**
     * Lấy sản phẩm theo category
     */
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * Lấy sản phẩm liên quan (cùng category)
     */
    public List<Product> getRelatedProducts(Long categoryId, Long excludeProductId) {
        return productRepository.findTop4ByCategoryIdAndIdNot(categoryId, excludeProductId);
    }

    /**
     * Tạo sản phẩm mới
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Cập nhật sản phẩm
     */
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Xóa sản phẩm
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Kiểm tra sản phẩm có tồn kho không
     */
    public boolean isProductInStock(Long productId, int quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        return productOpt.isPresent() && productOpt.get().getStock() >= quantity;
    }

    /**
     * Giảm số lượng tồn kho khi đặt hàng
     */
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));
        
        if (product.getStock() < quantity) {
            throw new RuntimeException("Không đủ hàng trong kho!");
        }
        
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    /**
     * Tăng số lượng tồn kho khi hủy đơn
     */
    public void increaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));
        
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }

    /**
     * Đếm tổng số sản phẩm
     */
    public long countProducts() {
        return productRepository.count();
    }
}
