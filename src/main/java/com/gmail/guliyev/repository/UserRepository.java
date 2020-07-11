package com.gmail.guliyev.repository;

import com.gmail.guliyev.entity.User;
import com.gmail.guliyev.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);

    Optional<User> findByName(String name);

    User findByUserRole(UserRoles userRole);

    boolean existsByUserRole(UserRoles userRole);

}
