package com.tamiya.springsecuityauthex.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "prelogin")
@Slf4j
public class PreLoginController {

  @GetMapping
  public String preLogin(HttpServletRequest request) {
    log.info(request.toString());
    DefaultCsrfToken token = (DefaultCsrfToken) request.getAttribute("_csrf");
    if (token == null) {
      throw new RuntimeException("could not get a token");
    }
    return token.getToken();
  }

}
