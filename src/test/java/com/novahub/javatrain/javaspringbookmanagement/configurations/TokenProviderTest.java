package com.novahub.javatrain.javaspringbookmanagement.configurations;

import com.novahub.javatrain.javaspringbookmanagement.fakes.JwtFaker;
import com.novahub.javatrain.javaspringbookmanagement.fakes.UserFaker;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Date;
import java.util.stream.Collectors;

import static com.novahub.javatrain.javaspringbookmanagement.constants.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.novahub.javatrain.javaspringbookmanagement.constants.Constants.AUTHORITIES_KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class TokenProviderTest {
    
    @InjectMocks
    TokenProvider tokenProvider;
    
    @Test
    public void getUsernameFromToken_Success() {
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        assertEquals(tokenProvider.getUsernameFromToken(JwtFaker.token), "kien303@gmail.com");
    }
    
    @Test
    public void getClaimsFromJwtToken_Success() {
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        assertEquals(tokenProvider.getClaimsFromJwtToken(JwtFaker.token).getSubject(), "kien303@gmail.com");
    }
    
    @Test
    public void validateToken_fail() {
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        UserDetails userDetails = Mockito.mock(UserDetails.class);
        assertEquals(tokenProvider.validateToken(JwtFaker.token, userDetails), false);
    }
    
    @Test
    public void isTokenExpired_fail() {
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        assertEquals(tokenProvider.isTokenExpired(JwtFaker.token), false);
        
    }
    
    @Test
    public void getAllClaimsFromToken_success() {
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        assertEquals(tokenProvider.getAllClaimsFromToken(JwtFaker.token).getSubject(), "kien303@gmail.com");
    }
    
    @Test
    public void getExpirationDateFromToken_Success() {
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        String target = "Wed Aug 10 09:29:17 ICT 2022";
        assertEquals(tokenProvider.getExpirationDateFromToken(JwtFaker.token).toString(), target);
    }
    
    @Test
    public void getClaimFromToken_success() {
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        String target = "Wed Aug 10 09:29:17 ICT 2022";
        assertEquals(tokenProvider.getClaimFromToken(JwtFaker.token, Claims::getExpiration).toString(), target);
        
    }
    
    @Test
    public void generateToken_success() {
        String signingKey = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJCb29rU3RvcmUifQ.xW8fVhGB66Ie3vL8hZoAOuntDXokWaxv3Nkl5V3V_ao";
    
        ReflectionTestUtils.setField(tokenProvider, "signingKey", JwtFaker.signingKey);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(UserFaker.createUser().getEmail(), UserFaker.createUser().getPassword()));
        Authentication authentication = Mockito.mock(Authentication.class);
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        String jwt = Jwts.builder()
                .setSubject(UserFaker.createUser().getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .compact();
        
        assertEquals(tokenProvider.generateToken(authentication, UserFaker.createUser().getEmail()), jwt);
    }
}
