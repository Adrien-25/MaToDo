package com.example.Todo_app_spring.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.Todo_app_spring.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Override
    void deleteById(@NonNull Long userId);

    @Modifying
    @Query("UPDATE User u SET u.username = :username WHERE u.id = :userId")
    void updateUsername(@Param("username") String username, @Param("userId") Long userId);
    
    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :userId")
    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId")
    void updatePassword(@Param("password") String password, @Param("userId") Long userId);
}
