package com.tamiya.springsecuityauthex.service;

import java.util.Optional;

import com.tamiya.springsecuityauthex.entity.User;

public interface UserService {
  Optional<User> findByName(String name);
}
