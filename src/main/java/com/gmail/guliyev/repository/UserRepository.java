package com.example.restaurant.repository;

import com.example.restaurant.entity.User;
import com.example.restaurant.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);

    Optional<User> findByName(String name);

    User findByUserRole(UserRoles userRole);

    boolean existsByUserRole(UserRoles userRole);

}
