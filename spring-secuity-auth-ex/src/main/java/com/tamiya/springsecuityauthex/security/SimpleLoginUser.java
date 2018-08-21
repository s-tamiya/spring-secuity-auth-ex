package com.tamiya.springsecuityauthex.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SimpleLoginUser extends User {

  private com.tamiya.springsecuityauthex.entity.User user;

  public com.tamiya.springsecuityauthex.entity.User getUser() {
    return this.user;
  }

  public SimpleLoginUser(com.tamiya.springsecuityauthex.entity.User user) {
    super(user.getName(), user.getPassword(), determineRoles(user.getAdmin()));
    this.user = user;
    System.out.println("Login User : name=" + this.user.getName() + " " + " auth=" + this.getAuthorities());
  }

  private static final List<GrantedAuthority> USER_ROLE = AuthorityUtils.createAuthorityList("ROLE_USER");
  private static final List<GrantedAuthority> ADMIN_ROLE = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");

  private static List<GrantedAuthority> determineRoles(boolean isAdmin) {
    return isAdmin ? ADMIN_ROLE : USER_ROLE;
  }

}
