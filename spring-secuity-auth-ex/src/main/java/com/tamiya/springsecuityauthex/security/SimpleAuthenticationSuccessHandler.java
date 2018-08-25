package com.tamiya.springsecuityauthex.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.data.repository.init.UnmarshallerRepositoryPopulatorFactoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	final private Algorithm algorithm;

	public SimpleAuthenticationSuccessHandler(String secretKey) {
		Objects.requireNonNull(secretKey, "secret key must not be null");
		
		try {
			this.algorithm = Algorithm.HMAC512(secretKey);
		} catch (IllegalIdentifierException e) {
			throw new RuntimeException(e);
		}
	}
	
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
  	
  	if (response.isCommitted()) {
  		log.info("response has already been commited");
  		return;
  	}
  	
  	setToken(response, generateToken(authentication));
  	response.setStatus(HttpStatus.OK.value());
  	clearAuthenticationAttribute(request);
  }
  
  private static final Long EXPIRATION_TIME = 1000L * 60L * 10L;
  
  private String generateToken(Authentication auth) {
  	SimpleLoginUser loginUser = (SimpleLoginUser) auth.getPrincipal();
  	Date issueAt = new Date();
  	Date notBefore = new Date(issueAt.getTime());
  	Date expireDate = new Date(issueAt.getTime() + EXPIRATION_TIME);
  	
  	String token = JWT.create()
  			.withIssuedAt(issueAt)
  			.withNotBefore(notBefore)
  			.withExpiresAt(expireDate)
  			.withSubject(loginUser.getUser().getId().toString())
  			.sign(this.algorithm);
  	
  	log.debug("generate token : {}", token);
  	return token;
  }
  
  private void setToken(HttpServletResponse response, String token) {
  	response.setHeader("Authorization", token);
  }

  private void clearAuthenticationAttribute(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session == null) {
      return;
    }

    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

}
