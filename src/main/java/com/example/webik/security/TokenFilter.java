package com.example.webik.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.example.webik.security.JwtConstant.HEADER_STRING;
import static com.example.webik.security.JwtConstant.TOKEN_PREFIX;

@Component
public class TokenFilter extends GenericFilterBean {
    @Autowired
    private JwtConstant jwtConstant;
    @Autowired
    private AppUserDetailsService appUserDetailsService;

    public TokenFilter(JwtConstant jwtConstant, AppUserDetailsService appUserDetailsService) {
        this.jwtConstant = jwtConstant;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = retrieveToken((HttpServletRequest) request);
        DecodedJWT decodedJWT = jwtConstant.decodeJWT(token);

        Authentication authentication = getAuthentication(decodedJWT);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    private Authentication getAuthentication(DecodedJWT jwt) {
        String username = jwt.getSubject();
        String authoritiesClaim = jwt.getClaim("authorities").asString();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(authoritiesClaim.split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        AppUserDetails principal = new AppUserDetails(username, authorities);
        return new UsernamePasswordAuthenticationToken(principal, jwt.getToken(), authorities);

    }

    public String retrieveToken(HttpServletRequest hRequest) {
        String authHeader = hRequest.getHeader(HEADER_STRING);
        if (!StringUtils.hasText(authHeader)) {
            throw new RuntimeException("Authorization header missing");
        }
        if (!authHeader.startsWith(TOKEN_PREFIX)) {
            throw new RuntimeException("Wrong token Authorization header");
        }
        return authHeader.substring(7);
    }
}
