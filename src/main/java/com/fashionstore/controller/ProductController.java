package com.fashionstore.controller;

import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.service.CategoryService;
import com.fashionstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Product Controller - Danh sách sản phẩm và chi tiết sản phẩm
 * Chuyển đổi từ Flask routes '/products', '/product/<id>'
 */
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public String productsPage(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String search,
            @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            Model model) {

        // Tìm kiếm sản phẩm với các điều kiện
        List<Product> products = productService.searchProducts(category, search, minPrice, maxPrice);
        
        // Lấy tất cả categories cho filter
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("search", search);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "products";
    }

    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
            .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));

        // Lấy sản phẩm liên quan (cùng category)
        List<Product> relatedProducts = productService.getRelatedProducts(
            product.getCategory().getId(), 
            product.getId()
        );

        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);

        return "product_detail";
    }
}
