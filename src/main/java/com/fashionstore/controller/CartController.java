package com.fashionstore.controller;

import com.fashionstore.model.Cart;
import com.fashionstore.model.User;
import com.fashionstore.service.CartService;
import com.fashionstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

/**
 * Cart Controller - Quản lý giỏ hàng
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    /**
     * Hiển thị giỏ hàng
     */
    @GetMapping
    public String viewCart(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        // Lấy tất cả items trong giỏ hàng
        List<Cart> cartItems = cartService.getCartItems(currentUser.getId());
        BigDecimal cartTotal = cartService.calculateCartTotal(currentUser.getId());

        // Debug log
        System.out.println("=== CART DEBUG ===");
        System.out.println("User ID: " + currentUser.getId());
        System.out.println("Username: " + currentUser.getUsername());
        System.out.println("Total items in cart: " + cartItems.size());
        
        // In chi tiết từng item
        for (int i = 0; i < cartItems.size(); i++) {
            Cart item = cartItems.get(i);
            System.out.println("Item " + (i+1) + ":");
            System.out.println("  - Product: " + (item.getProduct() != null ? item.getProduct().getName() : "NULL"));
            System.out.println("  - Quantity: " + item.getQuantity());
            System.out.println("  - Size: " + item.getSize());
            System.out.println("  - Color: " + item.getColor());
        }
        System.out.println("Cart Total: " + cartTotal);
        System.out.println("==================");

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("itemCount", cartItems.size());
        model.addAttribute("user", currentUser);  // Thêm user info để pre-fill form

        return "cart";
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId,
                           @RequestParam(defaultValue = "1") int quantity,
                           @RequestParam(required = false) String size,
                           @RequestParam(required = false) String color,
                           RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để thêm vào giỏ hàng!");
            return "redirect:/login";
        }

        try {
            // Set default values nếu không có
            if (size == null || size.trim().isEmpty()) {
                size = "M";
            }
            if (color == null || color.trim().isEmpty()) {
                color = "Mặc định";
            }

            cartService.addToCart(currentUser.getId(), productId, quantity, size, color);
            redirectAttributes.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");
            return "redirect:/cart";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
            return "redirect:/product/" + productId;
        }
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    @PostMapping("/update/{cartId}")
    public String updateCartItem(@PathVariable Long cartId,
                                @RequestParam int quantity,
                                RedirectAttributes redirectAttributes) {
        try {
            cartService.updateCartItemQuantity(cartId, quantity);
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật giỏ hàng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/cart";
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @PostMapping("/remove/{cartId}")
    public String removeCartItem(@PathVariable Long cartId,
                                RedirectAttributes redirectAttributes) {
        try {
            cartService.removeCartItem(cartId);
            redirectAttributes.addFlashAttribute("success", "Đã xóa sản phẩm khỏi giỏ hàng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/cart";
    }

    /**
     * Xóa tất cả sản phẩm trong giỏ hàng
     */
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }

        try {
            cartService.clearCart(currentUser.getId());
            redirectAttributes.addFlashAttribute("success", "Đã xóa tất cả sản phẩm khỏi giỏ hàng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/cart";
    }

    /**
     * Mua ngay - Thêm vào giỏ hàng và chuyển đến trang thanh toán
     */
    @PostMapping("/buy-now")
    public String buyNow(@RequestParam Long productId,
                        @RequestParam(defaultValue = "1") int quantity,
                        @RequestParam(required = false) String size,
                        @RequestParam(required = false) String color,
                        RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để mua hàng!");
            return "redirect:/login";
        }

        try {
            // Set default values nếu không có
            if (size == null || size.trim().isEmpty()) {
                size = "M";
            }
            if (color == null || color.trim().isEmpty()) {
                color = "Mặc định";
            }

            // Thêm sản phẩm vào giỏ hàng
            cartService.addToCart(currentUser.getId(), productId, quantity, size, color);
            
            // Chuyển hướng đến trang thanh toán
            return "redirect:/checkout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
            return "redirect:/product/" + productId;
        }
    }

    /**
     * Lấy thông tin user hiện tại
     */
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        
        String username = authentication.getName();
        return userService.findByUsername(username).orElse(null);
    }
}
