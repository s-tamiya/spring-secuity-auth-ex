package com.tamiya.springsecuityauthex.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SimpleLoginUser extends User {

  private com.tamiya.springsecuityauthex.entity.User user;

  public User getUser() {
    return this.getUser();
  }

  public SimpleLoginUser(com.tamiya.springsecuityauthex.entity.User user) {
    super(user.getName(), user.getPassword(), determineRoles(user.getAdmin()));
    this.user = user;
  }

  private static final List<GrantedAuthority> USER_ROLE = AuthorityUtils.createAuthorityList("ROLE_USER");
  private static final List<GrantedAuthority> ADMIN_ROLE = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");

  private static List<GrantedAuthority> determineRoles(boolean isAdmin) {
    return isAdmin ? ADMIN_ROLE : USER_ROLE;
  }

}
