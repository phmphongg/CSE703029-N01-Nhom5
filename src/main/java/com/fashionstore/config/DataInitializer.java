package com.fashionstore.config;

import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.model.User;
import com.fashionstore.repository.CategoryRepository;
import com.fashionstore.repository.ProductRepository;
import com.fashionstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Data Initializer - Khởi tạo dữ liệu mẫu khi start application
 * Chuyển đổi từ Python init_db()
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing database with sample data...");

        // Tạo admin user nếu chưa có
        initAdmin();

        // Tạo categories mặc định
        initCategories();

        // Tạo sample products
        initProducts();

        log.info("Database initialization completed!");
    }

    private void initAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrator");
            admin.setRole("admin");
            userRepository.save(admin);
            log.info("Admin user created: username=admin, password=admin123");
        }
    }

    private void initCategories() {
        List<String[]> defaultCategories = Arrays.asList(
            new String[]{"Áo Nam", "Các loại áo dành cho nam"},
            new String[]{"Áo Nữ", "Các loại áo dành cho nữ"},
            new String[]{"Quần Nam", "Các loại quần dành cho nam"},
            new String[]{"Quần Nữ", "Các loại quần dành cho nữ"},
            new String[]{"Giày Dép", "Giày và dép thời trang"},
            new String[]{"Phụ Kiện", "Phụ kiện thời trang"}
        );

        for (String[] cat : defaultCategories) {
            if (!categoryRepository.existsByName(cat[0])) {
                Category category = new Category();
                category.setName(cat[0]);
                category.setDescription(cat[1]);
                categoryRepository.save(category);
                log.info("Category created: {}", cat[0]);
            }
        }
    }

    private void initProducts() {
        if (productRepository.count() > 0) {
            return; // Skip if products already exist
        }

        Category aoNam = categoryRepository.findByName("Áo Nam").orElse(null);
        Category aoNu = categoryRepository.findByName("Áo Nữ").orElse(null);
        Category quanNam = categoryRepository.findByName("Quần Nam").orElse(null);
        Category quanNu = categoryRepository.findByName("Quần Nữ").orElse(null);
        Category giayDep = categoryRepository.findByName("Giày Dép").orElse(null);

        List<Product> sampleProducts = Arrays.asList(
            createProduct("Áo Thun Nam Basic", "Áo thun nam basic chất cotton thoáng mát", 
                         299000, aoNam, "/images/ao-thun-nam-1.jpg", 50, "S,M,L,XL", "Trắng,Đen,Xám"),
            createProduct("Áo Sơ Mi Nam Công Sở", "Áo sơ mi nam form slim fit", 
                         599000, aoNam, "/images/ao-somi-nam-1.jpg", 30, "S,M,L,XL,XXL", "Trắng,Xanh,Hồng"),
            createProduct("Áo Thun Nữ Croptop", "Áo thun nữ croptop thời trang", 
                         259000, aoNu, "/images/ao-thun-nu-1.jpg", 40, "S,M,L", "Hồng,Trắng,Đen"),
            createProduct("Quần Jean Nam Skinny", "Quần jean nam skinny fit", 
                         799000, quanNam, "/images/quan-jean-nam-1.png", 25, "29,30,31,32,33,34", "Xanh đậm,Xanh nhạt"),
            createProduct("Váy Nữ Vintage", "Váy nữ vintage xinh xắn", 
                         450000, quanNu, "/images/vay-nu-1.jpg", 20, "S,M,L", "Đỏ,Vàng,Xanh"),
            createProduct("Giày Sneaker Unisex", "Giày sneaker thể thao", 
                         1299000, giayDep, "/images/giay-sneaker-1.jpg", 15, "36,37,38,39,40,41,42,43", "Trắng,Đen,Xám")
        );

        productRepository.saveAll(sampleProducts);
        log.info("Sample products created: {} products", sampleProducts.size());
    }

    private Product createProduct(String name, String description, double price, 
                                 Category category, String imageUrl, int stock, 
                                 String size, String color) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        product.setCategory(category);
        product.setImageUrl(imageUrl);
        product.setStock(stock);
        product.setSize(size);
        product.setColor(color);
        return product;
    }
}
