package com.fashionstore.service;

import com.fashionstore.model.*;
import com.fashionstore.repository.OrderRepository;
import com.fashionstore.repository.OrderItemRepository;
import com.fashionstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Order Service - Business logic cho Order
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ProductService productService;

    /**
     * Tạo đơn hàng mới từ giỏ hàng
     */
    public Order createOrder(Long userId, String customerName, String customerPhone, String customerAddress) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

        List<Cart> cartItems = cartService.getCartItems(userId);
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        // Tạo order
        Order order = new Order();
        order.setUser(user);
        order.setCustomerName(customerName);
        order.setCustomerPhone(customerPhone);
        order.setCustomerAddress(customerAddress);
        order.setStatus("pending");

        // Tính tổng tiền và tạo order items
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Cart cartItem : cartItems) {
            Product product = cartItem.getProduct();

            // Kiểm tra tồn kho
            if (!productService.isProductInStock(product.getId(), cartItem.getQuantity())) {
                throw new RuntimeException("Sản phẩm " + product.getName() + " không đủ hàng!");
            }

            // Tạo order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setSize(cartItem.getSize());
            orderItem.setColor(cartItem.getColor());

            order.getOrderItems().add(orderItem);

            // Cộng vào tổng tiền
            totalAmount = totalAmount.add(
                product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );

            // Giảm số lượng tồn kho
            productService.decreaseStock(product.getId(), cartItem.getQuantity());
        }

        order.setTotalAmount(totalAmount);

        // Lưu order
        Order savedOrder = orderRepository.save(order);

        // Xóa giỏ hàng sau khi đặt hàng thành công
        cartService.clearCart(userId);

        return savedOrder;
    }

    /**
     * Lấy đơn hàng theo ID
     */
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    /**
     * Lấy tất cả đơn hàng của user
     */
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Lấy tất cả đơn hàng (admin)
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Lấy đơn hàng mới nhất (admin dashboard)
     */
    public List<Order> getRecentOrders() {
        return orderRepository.findTop5ByOrderByCreatedAtDesc();
    }

    /**
     * Cập nhật trạng thái đơn hàng
     */
    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Đơn hàng không tồn tại!"));

        String oldStatus = order.getStatus();
        order.setStatus(newStatus);

        // Nếu hủy đơn, hoàn lại tồn kho
        if ("cancelled".equalsIgnoreCase(newStatus) && !"cancelled".equalsIgnoreCase(oldStatus)) {
            for (OrderItem item : order.getOrderItems()) {
                productService.increaseStock(item.getProduct().getId(), item.getQuantity());
            }
        }

        return orderRepository.save(order);
    }

    /**
     * Đếm tổng số đơn hàng
     */
    public long countOrders() {
        return orderRepository.count();
    }

    /**
     * Tính tổng doanh thu từ đơn hàng đã hoàn thành
     */
    public BigDecimal getTotalRevenue() {
        return orderRepository.sumTotalAmountByStatus("completed");
    }

    /**
     * Xóa đơn hàng
     */
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}
