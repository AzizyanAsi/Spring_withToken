package com.example.webik.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.webik.controller.vm.UsernamePasswordVM;
import com.example.webik.security.JwtConstant;
import com.example.webik.security.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;

public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtConstant jwtConstant;

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody @Valid UsernamePasswordVM credentials) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return createToken(authentication);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + (125 * 60 * 1000));

        return JWT.create()
                .withSubject(authentication.getName())
                .withClaim("authorities", authorities)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC512(jwtConstant.getSecret()));

    }

}
