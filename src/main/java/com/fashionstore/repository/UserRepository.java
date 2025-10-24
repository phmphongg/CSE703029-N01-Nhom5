package com.fashionstore.repository;

import com.fashionstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User Repository - Interface cho các thao tác CRUD với User
 * Spring Data JPA tự động tạo implementation
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Tìm user theo username
     */
    Optional<User> findByUsername(String username);

    /**
     * Tìm user theo email
     */
    Optional<User> findByEmail(String email);

    /**
     * Tìm user theo username hoặc email
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    /**
     * Kiểm tra username đã tồn tại chưa
     */
    boolean existsByUsername(String username);

    /**
     * Kiểm tra email đã tồn tại chưa
     */
    boolean existsByEmail(String email);

    /**
     * Lấy danh sách users theo role
     */
    List<User> findByRole(String role);

    /**
     * Đếm số lượng users có role = 'user'
     */
    long countByRole(String role);
}
