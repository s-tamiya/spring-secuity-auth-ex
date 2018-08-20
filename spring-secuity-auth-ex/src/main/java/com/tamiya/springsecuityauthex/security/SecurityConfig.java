package com.tamiya.springsecuityauthex.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure (HttpSecurity http) throws Exception {
       http
         //.antMatcher("/h2-console/**").headers().frameOptions().disable()
       //.and()
           .authorizeRequests()
           .antMatchers("/prelogin", "/hello/**")
             .permitAll()
           .antMatchers("/user/**")
             .hasRole("USER")
           .antMatchers("/admin/**")
             .hasRole("ADMIN")
           .anyRequest()
             .authenticated()
         .and()
           // exception
           .exceptionHandling()
             .authenticationEntryPoint(authenticationEntryPoint())
             .accessDeniedHandler(accessDeniedHandler())
           // login
         .and()
           .formLogin()
             .loginProcessingUrl("/login").permitAll()
               .usernameParameter("email")
               .passwordParameter("pass")
           .successHandler(authenticationSuccessHandler())
           .failureHandler(authenticationFailureHandler())
         .and()
           .logout()
             .logoutUrl("/logout")
             .invalidateHttpSession(true)
             .deleteCookies("JSESSIONID")
             .logoutSuccessHandler(logoutSuccessHandler())
         .and()
           .csrf()
             //.disable();
             //.ignoringAntMatchers("/login")
             .csrfTokenRepository(new CookieCsrfTokenRepository())
       ;

    }

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth,
        @Qualifier("simpleUserDetailService") UserDetailsService userDetailService,
        PasswordEncoder passwordEncoder
    ) throws Exception {
      auth.eraseCredentials(true)
        .userDetailsService(userDetailService)
        .passwordEncoder(passwordEncoder);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    AuthenticationEntryPoint authenticationEntryPoint() {
        return new SimpleAuthenticationEntryPoint();
    }

    AccessDeniedHandler accessDeniedHandler() {
        return new SimpleAccessDeniedHandler();
    }

    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleAuthenticationSuccessHandler();
    }

    AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleAuthenticationFailureHandler();
    }

    LogoutSuccessHandler logoutSuccessHandler() {
        return new HttpStatusReturningLogoutSuccessHandler();
    }

}
