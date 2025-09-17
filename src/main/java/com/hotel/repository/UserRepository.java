package com.hotel.repository;

import com.hotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    // ✅ Fetch user by username
    Optional<User> findByUsername(String username);

    // ✅ (Optional) Fetch user by email if you want login with email too
    Optional<User> findByEmail(String email);
}
