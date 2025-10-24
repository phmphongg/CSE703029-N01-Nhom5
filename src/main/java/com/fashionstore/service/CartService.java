package com.fashionstore.service;

import com.fashionstore.model.Cart;
import com.fashionstore.model.Product;
import com.fashionstore.model.User;
import com.fashionstore.repository.CartRepository;
import com.fashionstore.repository.ProductRepository;
import com.fashionstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Cart Service - Business logic cho Cart
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * Lấy tất cả items trong giỏ hàng của user
     */
    public List<Cart> getCartItems(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    public Cart addToCart(Long userId, Long productId, int quantity, String size, String color) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User không tồn tại!"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));

        // Kiểm tra xem item đã có trong giỏ hàng chưa (cùng product, size, color)
        Optional<Cart> existingCartItem = cartRepository
            .findByUserIdAndProductIdAndSizeAndColor(userId, productId, size, color);

        if (existingCartItem.isPresent()) {
            // Nếu đã có, tăng số lượng
            Cart cart = existingCartItem.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartRepository.save(cart);
        } else {
            // Nếu chưa có, tạo mới
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setSize(size);
            cart.setColor(color);
            return cartRepository.save(cart);
        }
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    public Cart updateCartItemQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart item không tồn tại!"));
        
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0!");
        }
        
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    /**
     * Xóa item khỏi giỏ hàng
     */
    public void removeCartItem(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    /**
     * Xóa tất cả items trong giỏ hàng của user
     */
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    /**
     * Tính tổng giá trị giỏ hàng
     */
    public BigDecimal calculateCartTotal(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        
        return cartItems.stream()
            .map(item -> item.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Đếm số lượng items trong giỏ hàng
     */
    public long countCartItems(Long userId) {
        return cartRepository.countByUserId(userId);
    }
}
