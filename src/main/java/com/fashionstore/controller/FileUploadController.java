package com.fashionstore.controller;

import com.fashionstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * File Upload Controller
 * Xử lý upload ảnh sản phẩm
 * Chuyển đổi từ Flask allowed_file() và upload logic
 */
@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final ProductService productService;

    private static final String UPLOAD_DIR = "static/uploads/";
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpg", "jpeg", "gif");

    @PostMapping("/product-image")
    public String uploadProductImage(@RequestParam("file") MultipartFile file,
                                     RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn file để upload!");
            return "redirect:/admin/products";
        }

        if (!isAllowedFile(file.getOriginalFilename())) {
            redirectAttributes.addFlashAttribute("error", "Chỉ cho phép upload file ảnh (png, jpg, jpeg, gif)!");
            return "redirect:/admin/products";
        }

        try {
            String fileName = saveFile(file);
            redirectAttributes.addFlashAttribute("success", "Upload ảnh thành công!");
            redirectAttributes.addFlashAttribute("imageUrl", "/uploads/" + fileName);
            return "redirect:/admin/products";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi upload file: " + e.getMessage());
            return "redirect:/admin/products";
        }
    }

    private boolean isAllowedFile(String filename) {
        if (filename == null || !filename.contains(".")) {
            return false;
        }
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }

    private String saveFile(MultipartFile file) throws IOException {
        // Tạo thư mục upload nếu chưa có
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Tạo tên file unique để tránh trùng lặp
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // Lưu file
        Path filePath = Paths.get(UPLOAD_DIR + uniqueFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }
}
