package com.fashionstore.controller;

import com.fashionstore.model.Order;
import com.fashionstore.service.OrderService;
import com.fashionstore.service.ProductService;
import com.fashionstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Admin Controller - Trang quản trị
 * Chuyển đổi từ Flask route '/admin'
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;

    @GetMapping
    public String adminDashboard(Model model) {
        // Thống kê tổng quan
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productService.countProducts());
        stats.put("totalUsers", userService.countRegularUsers());
        stats.put("totalOrders", orderService.countOrders());
        stats.put("totalRevenue", orderService.getTotalRevenue());

        // Lấy 5 đơn hàng mới nhất
        List<Order> recentOrders = orderService.getRecentOrders();

        model.addAttribute("stats", stats);
        model.addAttribute("recentOrders", recentOrders);

        return "admin/dashboard";
    }
    
    /**
     * Quản lý đơn hàng - Hiển thị tất cả đơn hàng
     */
    @GetMapping("/orders")
    public String manageOrders(
            @RequestParam(required = false) String status,
            Model model) {
        
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            // Lọc theo trạng thái
            orders = orderService.getAllOrders().stream()
                .filter(order -> order.getStatus().equalsIgnoreCase(status))
                .toList();
        } else {
            // Lấy tất cả đơn hàng
            orders = orderService.getAllOrders();
        }
        
        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status);
        
        return "admin/orders";
    }
    
    /**
     * Xem chi tiết đơn hàng
     */
    @GetMapping("/orders/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
            .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại!"));
        
        model.addAttribute("order", order);
        return "admin/order_detail";
    }
    
    /**
     * Cập nhật trạng thái đơn hàng
     */
    @PostMapping("/orders/{orderId}/update-status")
    public String updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            RedirectAttributes redirectAttributes) {
        
        try {
            orderService.updateOrderStatus(orderId, status);
            redirectAttributes.addFlashAttribute("success", 
                "Đã cập nhật trạng thái đơn hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/admin/orders/" + orderId;
    }
    
    /**
     * Xác nhận đơn hàng (chuyển từ pending sang processing)
     */
    @PostMapping("/orders/{orderId}/confirm")
    public String confirmOrder(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(orderId, "processing");
            redirectAttributes.addFlashAttribute("success", 
                "Đã xác nhận đơn hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/admin/orders";
    }
    
    /**
     * Chuyển đơn hàng sang đang giao
     */
    @PostMapping("/orders/{orderId}/ship")
    public String shipOrder(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(orderId, "shipping");
            redirectAttributes.addFlashAttribute("success", 
                "Đã chuyển đơn hàng sang trạng thái đang giao!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/admin/orders";
    }
    
    /**
     * Hoàn thành đơn hàng
     */
    @PostMapping("/orders/{orderId}/complete")
    public String completeOrder(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(orderId, "completed");
            redirectAttributes.addFlashAttribute("success", 
                "Đã hoàn thành đơn hàng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/admin/orders";
    }
    
    /**
     * Hủy đơn hàng
     */
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(orderId, "cancelled");
            redirectAttributes.addFlashAttribute("success", 
                "Đã hủy đơn hàng!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/admin/orders";
    }
}
