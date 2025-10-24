package com.fashionstore.controller;

import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.service.CategoryService;
import com.fashionstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Home Controller - Trang chủ
 * Chuyển đổi từ Flask route '/'
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String index(Model model) {
        // Lấy 8 sản phẩm mới nhất
        List<Product> products = productService.getLatestProducts();
        
        // Lấy tất cả danh mục
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);

        return "index";
    }
}
