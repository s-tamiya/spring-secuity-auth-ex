package com.tamiya.springsecuityauthex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tamiya.springsecuityauthex.repository.UserRepository;

@Service("simpleUserDetailService")
public class SimpleUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public SimpleUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
    // TODO 自動生成されたメソッド・スタブ
    return userRepository.findByEmail(email)
              .map(SimpleLoginUser::new)
              .orElseThrow(() -> new UsernameNotFoundException("user not found"));
  }

}
