package com.example.clone_project.config;

import com.example.clone_project.jwt.JwtFilter;
import com.example.clone_project.jwt.TokenProvider;
import com.example.clone_project.security.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfiguration
        extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final String SECRET_KEY;
  private final TokenProvider tokenProvider;
  private final MemberDetailsService memberDetailsService;

  @Override
  public void configure(HttpSecurity httpSecurity) {
    JwtFilter customJwtFilter = new JwtFilter(SECRET_KEY, tokenProvider, memberDetailsService);
    httpSecurity.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
