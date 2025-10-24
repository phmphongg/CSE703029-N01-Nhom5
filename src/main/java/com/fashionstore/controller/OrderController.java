package com.fashionstore.controller;

import com.fashionstore.model.Order;
import com.fashionstore.model.User;
import com.fashionstore.service.CartService;
import com.fashionstore.service.OrderService;
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
 * Order Controller - Xử lý các request liên quan đến đơn hàng
 */
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;
    private final UserService userService;

    /**
     * Hiển thị trang thanh toán
     */
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User user = userService.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

        // Lấy giỏ hàng
        List<com.fashionstore.model.Cart> cartItems = cartService.getCartItems(user.getId());
        
        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng trống!");
            return "redirect:/cart";
        }

        // Tính tổng tiền
        BigDecimal cartTotal = cartService.calculateCartTotal(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("itemCount", cartItems.size());

        return "checkout";
    }

    /**
     * Xử lý đặt hàng
     */
    @PostMapping("/checkout")
    public String processCheckout(
            @RequestParam("customerName") String customerName,
            @RequestParam("customerPhone") String customerPhone,
            @RequestParam("customerAddress") String customerAddress,
            @RequestParam(value = "note", required = false) String note,
            RedirectAttributes redirectAttributes) {
        
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            
            User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

            // Tạo đơn hàng
            Order order = orderService.createOrder(
                user.getId(), 
                customerName, 
                customerPhone, 
                customerAddress
            );

            redirectAttributes.addFlashAttribute("success", 
                "Đặt hàng thành công! Mã đơn hàng: #" + order.getId());
            
            return "redirect:/order/confirmation/" + order.getId();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Lỗi: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

    /**
     * Hiển thị trang xác nhận đơn hàng
     */
    @GetMapping("/order/confirmation/{orderId}")
    public String showOrderConfirmation(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
            .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại!"));

        model.addAttribute("order", order);
        return "order_confirmation";
    }

    /**
     * Hiển thị danh sách đơn hàng của user
     */
    @GetMapping("/orders")
    public String showUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User user = userService.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

        List<Order> orders = orderService.getUserOrders(user.getId());
        
        model.addAttribute("orders", orders);
        return "orders";
    }

    /**
     * Xem chi tiết đơn hàng
     */
    @GetMapping("/order/{orderId}")
    public String showOrderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId)
            .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại!"));

        model.addAttribute("order", order);
        return "order_detail";
    }

    /**
     * Hủy đơn hàng
     */
    @PostMapping("/order/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.updateOrderStatus(orderId, "cancelled");
            redirectAttributes.addFlashAttribute("success", "Đã hủy đơn hàng thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/orders";
    }
}
