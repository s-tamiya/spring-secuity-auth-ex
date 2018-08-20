package com.tamiya.springsecuityauthex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tamiya.springsecuityauthex.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  Optional<User> findFirstByName(String name);
}
