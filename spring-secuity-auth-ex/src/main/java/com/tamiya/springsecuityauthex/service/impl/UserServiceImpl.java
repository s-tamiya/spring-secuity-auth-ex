package com.tamiya.springsecuityauthex.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tamiya.springsecuityauthex.entity.User;
import com.tamiya.springsecuityauthex.repository.UserRepository;
import com.tamiya.springsecuityauthex.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> findByName(String name) {
    Objects.requireNonNull(name, "name must not be null");
    return userRepository.findFirstByName(name);
  }

}
