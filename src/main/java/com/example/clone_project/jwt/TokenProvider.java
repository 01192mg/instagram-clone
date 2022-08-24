package com.example.clone_project.jwt;

import com.example.clone_project.dto.response.ResponseDto;
import com.example.clone_project.entity.Member;
import com.example.clone_project.security.MemberDetailsImpl;
import com.example.clone_project.shared.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

  private static final String AUTHORITIES_KEY = "auth";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;            //1일
  private static final long REFRESH_TOKEN_EXPRIRE_TIME = 1000 * 60 * 60 * 24 * 7;     //7일

  private final Key key;

//  private final UserDetailsServiceImpl userDetailsService;

  public TokenProvider(@Value("${jwt.secret}") String secretKey) {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenDto generateTokenDto(Member member) {
    long now = (new Date().getTime());

    Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
    String accessToken = Jwts.builder()
        .setSubject(member.getUsername())
        .claim(AUTHORITIES_KEY, Authority.ROLE_MEMBER.toString())
        .setExpiration(accessTokenExpiresIn)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    String refreshToken = Jwts.builder()
        .setExpiration(new Date(now + REFRESH_TOKEN_EXPRIRE_TIME))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    return TokenDto.builder()
        .grantType(BEARER_PREFIX)
        .accessToken(accessToken)
        .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
        .refreshToken(refreshToken)
        .build();

  }

//  public Authentication getAuthentication(String accessToken) {
//    Claims claims = parseClaims(accessToken);
//
//    if (claims.get(AUTHORITIES_KEY) == null) {
//      throw new RuntimeException("권한 정보가 없는 토큰 입니다.");
//    }
//
//    Collection<? extends GrantedAuthority> authorities =
//        Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
//            .map(SimpleGrantedAuthority::new)
//            .collect(Collectors.toList());
//
//    UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());
//
//    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
//  }

  public Member getMemberFromAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || AnonymousAuthenticationToken.class.
        isAssignableFrom(authentication.getClass())) {
      return null;
    }
    return ((MemberDetailsImpl) authentication.getPrincipal()).getMember();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token, 만료된 JWT token 입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
    } catch (IllegalArgumentException e) {
      log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
    }
    return false;
  }

//  private Claims parseClaims(String accessToken) {
//    try {
//      return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
//    } catch (ExpiredJwtException e) {
//      return e.getClaims();
//    }
//  }
}
